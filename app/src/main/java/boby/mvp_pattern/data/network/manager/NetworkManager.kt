package boby.mvp_pattern.data.network.manager

interface NetworkManager {
    fun getUsers(onGetUsersCallback: OnGetUsersCallback, since: String)
    fun getRepos(onGetReposCallback: OnGetReposCallback, user: String)
    fun getRateLimit(onGetRateLimitCallback: OnGetRateLimitCallback)
}