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
    private val clickedUser = User(135, "Bobby", "url-135")
    private val usersRepository = MockUsersRepository(userList)

    private val rate = Rate(100, 35, 75)
    private val rateLimitRepository = MockRateLimitRepository(rate)

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
        verify(mockMainActivity).showUsers(userList)
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
    fun testOnItemClick() {
        presenter.onItemClick(clickedUser)
        verify(mockMainActivity).openUserDetail(clickedUser)
    }

    @Test
    fun testNoActionsWithView() {
        Mockito.verifyNoMoreInteractions(mockMainActivity)
    }

    private class MockUsersRepository(val users: List<User>): UsersRepository{
        override fun getUsers(onUsersCallback: OnUsersCallback, since: String){
            onUsersCallback.onUsersReady(users)
        }
    }

    private class MockRateLimitRepository(val rate: Rate): RateLimitRepository{
        override fun getRate(onRateLimitCallback: OnRateLimitCallback){
            onRateLimitCallback.onRateLimitReady(rate)
        }
    }

}
