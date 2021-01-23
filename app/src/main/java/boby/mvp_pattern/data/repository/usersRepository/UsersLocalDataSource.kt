package boby.mvp_pattern.data.repository.usersRepository

import android.os.Handler
import android.util.Log
import boby.mvp_pattern.data.dataBase.AppDatabase
import boby.mvp_pattern.data.dataBase.dbModels.DBUser
import boby.mvp_pattern.data.domainModels.User
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.modelmapper.ModelMapper
import java.lang.reflect.Type
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(db: AppDatabase) {
    private val LOG_TAG = this.javaClass.name.split(".").last()
    private val testMode = false
    private val userDao = db.userDao()

    fun getUsers(onUsersLocalCallback: OnUsersLocalCallback) {
        if (testMode) {
            val userList = listOf(
                User(1, "Nata Local", ""),
                User(2, "Peter Local", ""),
                User(3, "Jon Local", "")
            )
            Handler().postDelayed({ onUsersLocalCallback.onUsersLocalReady(userList) }, 100)
        } else {
            //get users from DB
            GlobalScope.launch(Dispatchers.Main){
                var users = listOf<User>()
                try {
                    val dbUsers = withContext(Dispatchers.IO) { userDao.getAll() }
                    val modelMapper = ModelMapper()
                    val userListType = object : TypeToken<List<User>>(){}.type
                    users = modelMapper.map(dbUsers, userListType)
                } catch (exception: Exception) {
                    Log.w(LOG_TAG, "$exception handled !!!")
                }
                onUsersLocalCallback.onUsersLocalReady(users)
                Log.d(LOG_TAG, "getUsers done")
            }
        }
    }

    fun saveUsers(users: List<User>){
        //save users in DB
        GlobalScope.launch(Dispatchers.Main + handler){
            for (user in users){
                val userToSave = DBUser(user.userId, user.login, user.avatarUrl)
                withContext(Dispatchers.IO) { userDao.insert(userToSave) }
            }
            Log.d(LOG_TAG, "saveUsers done")
        }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "$exception handled !")
    }
}