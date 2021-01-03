package boby.mvp_pattern.presenter

import android.util.Log
import boby.mvp_pattern.contract.MainContract
import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.data.repository.rateLimitRepository.OnRateLimitCallback
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitRepository
import boby.mvp_pattern.data.repository.usersRepository.OnUsersCallback
import boby.mvp_pattern.data.repository.usersRepository.UsersRepository
import javax.inject.Inject

class MainPresenter @Inject constructor(private val usersRepository: UsersRepository,
                                        private val rateLimitRepository: RateLimitRepository
): MainContract.Presenter {
    private val LOG_TAG = "MainPresenter"
    private var view: MainContract.View? = null
    private var users = listOf<User>()

    init {
        Log.d(LOG_TAG, "init")
    }

    override fun onViewCreated() {
        loadUsers()
    }

    override fun onRefresh() {
        loadUsers()
    }

    override fun attachView(view: MainContract.View) {
        Log.d(LOG_TAG, "attachView")
        this.view = view
    }

    override fun detachView() {
        Log.d(LOG_TAG, "detachView")
        this.view = null
    }

    override fun onResume(){
        loadRate()
    }

    override fun onItemClick(user: User){
        view?.openUserDetail(user)
    }

    private fun loadUsers(){
        var since: Int = 0
        if (!users.isNullOrEmpty()) since = users.last().userId
        view?.showProgressIndicator(true)
        usersRepository.getUsers(object : OnUsersCallback {
            override fun onUsersReady(users: List<User>) {
                this@MainPresenter.users = users
                view?.showProgressIndicator(false)
                view?.showUsers(users)
            }

            override fun onUsersError(error: String) {
                view?.showProgressIndicator(false)
                view?.showError(error)
            }
        }, since.toString())
        loadRate()
    }

    private fun loadRate(){
        rateLimitRepository.getRate(object : OnRateLimitCallback {
            override fun onRateLimitReady(rate: Rate) {
                view?.showRate(rate)
            }
        })
    }
}