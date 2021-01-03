package boby.mvp_pattern.data.network

import boby.mvp_pattern.data.network.networkModels.NetworkRepo
import boby.mvp_pattern.data.network.networkModels.NetworkUser
import boby.mvp_pattern.data.network.networkModels.responses.RateLimitResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/users")
    fun getUsers(@Query("since") since: String): Call<List<NetworkUser>>

    @GET("/users/{user}/repos")
    fun getRepos(@Path("user") user: String): Call<List<NetworkRepo>>

    @GET("/rate_limit")
    fun getRateLimit(): Call<RateLimitResponse>
}