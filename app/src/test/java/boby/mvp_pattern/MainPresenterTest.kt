package boby.mvp_pattern


import boby.mvp_pattern.contract.MainContract
import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.data.repository.rateLimitRepository.OnRateLimitCallback
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitRepository
import boby.mvp_pattern.data.repository.usersRepository.OnUsersCallback
import boby.mvp_pattern.data.repository.usersRepository.UsersRepository
import boby.mvp_pattern.presenter.MainPresenter
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var mockMainActivity: MainContract.View

    private val userList = listOf(
        User(1, "Nata", "url-1"),
        User(2, "Peter", "url-2"),
        User(3, "John", "url-3")
    )
    private val userListNext = listOf(
        User(4, "Julia", "url-4"),
        User(5, "Robert", "url-5"),
        User(6, "Arni", "url-6")
    )
    private val clickedUser = User(135, "Bobby", "url-135")
    private val usersRepository = MockUsersRepository(userList, userListNext)

    private val rate = Rate(100, 35, 75)
    private val rateLimitRepository = MockRateLimitRepository(rate)
    private val error = "Some error"
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(usersRepository, rateLimitRepository)
        assertNotNull(presenter)
        presenter.attachView(mockMainActivity)
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun testOnViewCreatedFlow() {
        presenter.onViewCreated()
        verify(mockMainActivity, times(1)).showProgressIndicator(true)
        verify(mockMainActivity, times(1)).showProgressIndicator(false)
        verify(mockMainActivity, times(1)).showUsers(userList)
        verify(mockMainActivity, times(1)).showRate(rate)
    }

    @Test
    fun testOnResume() {
        presenter.onResume()
        verify(mockMainActivity).showRate(rate)
    }

    @Test
    fun testOnRefresh() {
        presenter.onRefresh()
        verify(mockMainActivity).showUsers(userList)
    }

    @Test
    fun testOnRefreshNext() {
        presenter.onRefresh()
        verify(mockMainActivity, times(1)).showUsers(userList)
        presenter.onRefresh()
        verify(mockMainActivity, times(1)).showUsers(userListNext)
    }

    @Test
    fun testOnRefreshWithError() {
        usersRepository.setError(error)
        presenter.onRefresh()
        verify(mockMainActivity, times(1)).showProgressIndicator(true)
        verify(mockMainActivity, times(1)).showProgressIndicator(false)
        verify(mockMainActivity, times(0)).showUsers(userList)
        verify(mockMainActivity, times(1)).showError(error)
    }

    @Test
    fun testOnItemClick() {
        presenter.onItemClick(clickedUser)
        verify(mockMainActivity).openUserDetail(clickedUser)
    }

    @Test
    fun testNoActionsWithView() {
        Mockito.verifyNoMoreInteractions(mockMainActivity)
    }

    private class MockUsersRepository(val users: List<User>, val usersNext: List<User>): UsersRepository{
        private var error = ""

        override fun getUsers(onUsersCallback: OnUsersCallback, since: String){
            if (error.isNotEmpty()) {
                onUsersCallback.onUsersError(error)
            } else {
                if (users.last().userId.toString() == since){
                    onUsersCallback.onUsersReady(usersNext)
                } else {
                    onUsersCallback.onUsersReady(users)
                }
            }
        }

        fun setError (error: String){
            this.error = error
        }
    }

    private class MockRateLimitRepository(val rate: Rate): RateLimitRepository{
        override fun getRate(onRateLimitCallback: OnRateLimitCallback){
            onRateLimitCallback.onRateLimitReady(rate)
        }
    }

}
