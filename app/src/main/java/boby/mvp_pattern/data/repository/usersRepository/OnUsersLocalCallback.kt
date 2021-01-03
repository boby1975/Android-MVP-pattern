package boby.mvp_pattern.data.repository.usersRepository

import boby.mvp_pattern.data.domainModels.User

interface OnUsersLocalCallback {
    fun onUsersLocalReady(users: List<User>)
}