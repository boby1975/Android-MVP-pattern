package boby.mvp_pattern.data.repository.reposRepository

import android.os.Handler
import boby.mvp_pattern.data.domainModels.Repo
import boby.mvp_pattern.data.network.manager.NetworkManager
import boby.mvp_pattern.data.network.manager.OnGetReposCallback
import boby.mvp_pattern.data.network.networkModels.NetworkRepo
import com.google.gson.reflect.TypeToken
import org.modelmapper.ModelMapper
import javax.inject.Inject

class ReposRemoteDataSource @Inject constructor( private val networkManager: NetworkManager) {
    private val testMode = false

    fun getRepos(onReposRemoteCallback: OnReposRemoteCallback, user: String) {
        if (testMode) {
            val repoList = listOf(
                Repo(1, "First", "Description 1", 5, 8, 9),
                Repo(2, "Second", "Description 2", 24, 56, 789),
                Repo(3, "Third", "Description 3", 54, 67, 789)
            )
            Handler().postDelayed({ onReposRemoteCallback.onReposRemoteReady(repoList) }, 2000)
        } else {
            networkManager.getRepos(object: OnGetReposCallback {
                override fun onReposReady(repos: List<NetworkRepo>) {
                    val modelMapper = ModelMapper()
                    val repoListType = object : TypeToken<List<Repo>>(){}.type
                    val domainRepos: List<Repo> = modelMapper.map(repos, repoListType)
                    onReposRemoteCallback.onReposRemoteReady(domainRepos)
                }

                override fun onReposError(error: String) {
                    onReposRemoteCallback.onReposRemoteError(error)
                }
            }, user)
        }
    }
}

