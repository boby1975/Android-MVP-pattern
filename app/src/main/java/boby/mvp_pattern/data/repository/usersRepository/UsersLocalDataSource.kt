package boby.mvp_pattern.data.repository.usersRepository

import android.content.SharedPreferences
import android.os.Handler
import boby.mvp_pattern.data.domainModels.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val testMode = false
    private val USERS = "users"

    fun getUsers(onUsersLocalCallback: OnUsersLocalCallback) {
        if (testMode) {
            val userList = listOf(
                User(1, "Nata Local", ""),
                User(2, "Peter Local", ""),
                User(3, "Jon Local", "")
            )
            Handler().postDelayed({ onUsersLocalCallback.onUsersLocalReady(userList) }, 100)
        } else {
            val jsonUsers = sharedPreferences.getString(USERS, "")
            val userListType = object : TypeToken<List<User>>(){}.type
            val users = Gson().fromJson<List<User>>(jsonUsers, userListType) ?: listOf()
            onUsersLocalCallback.onUsersLocalReady(users)
        }
    }

    fun saveUsers(users: List<User>){
        //todo save users in DB
        val editor = sharedPreferences.edit()
        val jsonUsers = Gson().toJson(users)
        editor.putString(USERS, jsonUsers)
        editor.apply()
    }
}