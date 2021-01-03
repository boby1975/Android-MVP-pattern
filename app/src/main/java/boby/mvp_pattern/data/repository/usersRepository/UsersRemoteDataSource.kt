package boby.mvp_pattern.data.repository.usersRepository

import android.os.Handler
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.data.network.manager.NetworkManager
import boby.mvp_pattern.data.network.manager.OnGetUsersCallback
import boby.mvp_pattern.data.network.networkModels.NetworkUser
import com.google.gson.reflect.TypeToken
import org.modelmapper.ModelMapper
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val networkManager: NetworkManager) {
    private val testMode = false

    fun getUsers(onUsersRemoteCallback: OnUsersRemoteCallback, since: String) {
        if (testMode) {
            val userList = listOf(
                User(1, "login-1", "url-1"),
                User(2, "login-2", "url-2"),
                User(3, "login-3", "url-3")
            )
            Handler().postDelayed({ onUsersRemoteCallback.onUsersRemoteReady(userList) }, 2000)
        } else {
            networkManager.getUsers(object: OnGetUsersCallback {
                override fun onUsersReady(users: List<NetworkUser>) {
                    val modelMapper = ModelMapper()
                    val userListType = object : TypeToken<List<User>>(){}.type
                    val domainUsers: List<User> = modelMapper.map(users, userListType)
                    onUsersRemoteCallback.onUsersRemoteReady(domainUsers)
                }

                override fun onUsersError(error: String) {
                    onUsersRemoteCallback.onUsersRemoteError(error)
                }
            }, since)
        }
    }
}