package boby.mvp_pattern.data.repository.usersRepository

import boby.mvp_pattern.data.domainModels.User

interface OnUsersCallback {
    fun onUsersReady(users: List<User>)
    fun onUsersError(error: String)
}