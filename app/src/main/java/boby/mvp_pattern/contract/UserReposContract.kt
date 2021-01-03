package boby.mvp_pattern.contract

import boby.mvp_pattern.data.domainModels.Repo
import boby.mvp_pattern.presenter.BasePresenter
import boby.mvp_pattern.view.BaseView

interface UserReposContract {
    interface Presenter : BasePresenter<View> {
        fun onViewCreated(user: String)
    }

    interface View : BaseView<Presenter> {
        fun showRepos(repositories: List<Repo>)
        fun showProgressIndicator(visible: Boolean)
        fun showError(error: String)
    }
}