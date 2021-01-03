package boby.mvp_pattern.data.repository.usersRepository

import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.data.network.NetState
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val netState: NetState,
    private val localDataSource: UsersLocalDataSource,
    private val remoteDataSource: UsersRemoteDataSource): UsersRepository {

    override fun getUsers(onUsersCallback: OnUsersCallback, since: String) {
        netState.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getUsers(object : OnUsersRemoteCallback {
                    override fun onUsersRemoteReady(users: List<User>) {
                        localDataSource.saveUsers(users)
                        onUsersCallback.onUsersReady(users)
                    }

                    override fun onUsersRemoteError(error: String) {
                        onUsersCallback.onUsersError(error)
                    }
                }, since)
            } else {
                localDataSource.getUsers(object : OnUsersLocalCallback {
                    override fun onUsersLocalReady(users: List<User>) {
                        onUsersCallback.onUsersReady(users)
                    }
                })
            }
        }
    }

}