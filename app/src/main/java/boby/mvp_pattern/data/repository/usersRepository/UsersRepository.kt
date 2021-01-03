package boby.mvp_pattern.data.repository.usersRepository

interface UsersRepository {
    fun getUsers(onUsersCallback: OnUsersCallback, since: String)
}