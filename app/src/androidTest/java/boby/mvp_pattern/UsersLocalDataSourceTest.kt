package boby.mvp_pattern

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import boby.mvp_pattern.data.dataBase.AppDatabase
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.data.repository.usersRepository.OnUsersLocalCallback
import boby.mvp_pattern.data.repository.usersRepository.UsersLocalDataSource
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersLocalDataSourceTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var mDatabase: AppDatabase
    private lateinit var mDataSource: UsersLocalDataSource
    private val testUsers = listOf(
        User(12, "Nata Local", "url-1"),
        User(71, "Peter Local", "url-2"),
        User(193, "Jon Local", "url-3")
    )

    @Before
    fun initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        mDataSource = UsersLocalDataSource(mDatabase)
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun saveAndGetUsers() {
        mDataSource.saveUsers(testUsers)
        Thread.sleep(500)
        var dbUsers = listOf<User>()
        mDataSource.getUsers(object : OnUsersLocalCallback {
            override fun onUsersLocalReady(users: List<User>) {
                dbUsers = users
            }
        })
        Thread.sleep(500)
        assertThat(dbUsers.size, `is`(3))
        assertEquals(dbUsers, testUsers)
    }
}