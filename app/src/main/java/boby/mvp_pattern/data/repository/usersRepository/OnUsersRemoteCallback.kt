package boby.mvp_pattern.data.repository.usersRepository

import boby.mvp_pattern.data.domainModels.User

interface OnUsersRemoteCallback {
    fun onUsersRemoteReady(users: List<User>)
    fun onUsersRemoteError(error: String)
}