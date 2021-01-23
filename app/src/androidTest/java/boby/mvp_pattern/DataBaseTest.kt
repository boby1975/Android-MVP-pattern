package boby.mvp_pattern

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import boby.mvp_pattern.data.dataBase.AppDatabase
import boby.mvp_pattern.data.dataBase.dbModels.DBUser
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DataBaseTest {
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var mDatabase: AppDatabase
    private val testUser = DBUser(135, "Bobby", "url-135")

    @Before
    fun initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun insertAndGetUser() {
        mDatabase.userDao().insert(testUser)

        val users: List<DBUser> = mDatabase.userDao().getAll()
        assertThat(users.size, `is`(1))
        val dbUser = users[0]
        assertEquals(dbUser, testUser)
    }

}