package boby.mvp_pattern.data.repository.reposRepository

import boby.mvp_pattern.data.domainModels.Repo
import boby.mvp_pattern.data.network.NetState
import javax.inject.Inject

class ReposRepository @Inject constructor(
    private val netState: NetState,
    private val localDataSource: ReposLocalDataSource,
    private val remoteDataSource: ReposRemoteDataSource) {

    fun getRepos(onReposCallback: OnReposCallback, login: String, userId: Int) {
        netState.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getRepos(object: OnReposRemoteCallback {
                    override fun onReposRemoteReady(repos: List<Repo>) {
                        localDataSource.saveRepos(repos, userId)
                        onReposCallback.onReposReady(repos)
                    }

                    override fun onReposRemoteError(error: String) {
                        onReposCallback.onReposError(error)
                    }
                }, login)
            } else {
                localDataSource.getRepos(object: OnReposLocalCallback {
                    override fun onReposLocalReady(repos: List<Repo>) {
                        onReposCallback.onReposReady(repos)
                    }
                }, userId)
            }
        }
    }

}