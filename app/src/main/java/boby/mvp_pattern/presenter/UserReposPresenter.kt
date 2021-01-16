package boby.mvp_pattern.presenter

import android.util.Log
import boby.mvp_pattern.contract.UserReposContract
import boby.mvp_pattern.data.domainModels.Repo
import boby.mvp_pattern.data.repository.reposRepository.OnReposCallback
import boby.mvp_pattern.data.repository.reposRepository.ReposRepository
import javax.inject.Inject

class UserReposPresenter @Inject constructor(private val reposRepository: ReposRepository): UserReposContract.Presenter {
    private val LOG_TAG = "UserReposPresenter"
    private var view: UserReposContract.View? = null
    private var repositories = listOf<Repo>()

    init {
        Log.d(LOG_TAG, "init")
    }

    override fun onViewCreated(login: String, userId: Int) {
        loadRepositories(login, userId)
    }

    override fun attachView(view: UserReposContract.View) {
        Log.d(LOG_TAG, "attachView")
        this.view = view
    }

    override fun detachView() {
        Log.d(LOG_TAG, "detachView")
        this.view = null
    }

    private fun loadRepositories(login: String, userId: Int) {
        view?.showProgressIndicator(true)
        reposRepository.getRepos(object: OnReposCallback {
            override fun onReposReady(repos: List<Repo>) {
                repositories = repos
                view?.showProgressIndicator(false)
                view?.showRepos(repositories)
            }

            override fun onReposError(error: String) {
                view?.showProgressIndicator(false)
                view?.showError(error)
            }
        }, login, userId)
    }
}