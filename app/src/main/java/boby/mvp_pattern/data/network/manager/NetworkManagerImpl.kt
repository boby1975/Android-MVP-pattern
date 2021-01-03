package boby.mvp_pattern.data.network.manager

import android.util.Log
import boby.mvp_pattern.data.network.GithubApi
import boby.mvp_pattern.data.network.networkModels.NetworkRepo
import boby.mvp_pattern.data.network.networkModels.NetworkUser
import boby.mvp_pattern.data.network.networkModels.responses.RateLimitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NetworkManagerImpl @Inject constructor(private val githubApi: GithubApi): NetworkManager {
    private val LOG_TAG = "NetworkManagerImpl"

    override fun getUsers(onGetUsersCallback: OnGetUsersCallback, since: String) {
        githubApi.getUsers(since).enqueue(object: Callback<List<NetworkUser>> {
            override fun onResponse(call: Call<List<NetworkUser>>, response: Response<List<NetworkUser>>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    onGetUsersCallback.onUsersReady(body)
                } else {
                    onGetUsersCallback.onUsersError("Code=${response.code()}, message=${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<NetworkUser>>, t: Throwable) {
                Log.w(LOG_TAG, "getUsers, error = " + t.message)
                onGetUsersCallback.onUsersError(t.message.toString())
            }
        })
    }

    override fun getRepos(onGetReposCallback: OnGetReposCallback, user: String){
        githubApi.getRepos(user).enqueue(object: Callback<List<NetworkRepo>> {
            override fun onResponse(call: Call<List<NetworkRepo>>, response: Response<List<NetworkRepo>>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    onGetReposCallback.onReposReady(body)
                } else {
                    onGetReposCallback.onReposError("Code=${response.code()}, message=${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<NetworkRepo>>, t: Throwable) {
                Log.w(LOG_TAG, "getRepos, error = " + t.message)
                onGetReposCallback.onReposError(t.message.toString())
            }
        })
    }


    override fun getRateLimit(onGetRateLimitCallback: OnGetRateLimitCallback){
        githubApi.getRateLimit().enqueue(object: Callback<RateLimitResponse> {
            override fun onResponse(call: Call<RateLimitResponse>, response: Response<RateLimitResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    onGetRateLimitCallback.onRateLimitReady(body.rate)
                }
            }

            override fun onFailure(call: Call<RateLimitResponse>, t: Throwable) {
                Log.w(LOG_TAG, "getRateLimit, error = " + t.message)
            }
        })
    }
}
