package boby.mvp_pattern.contract

import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.presenter.BasePresenter
import boby.mvp_pattern.view.BaseView

interface MainContract {
    interface Presenter : BasePresenter<View> {
        fun onViewCreated()
        fun onRefresh()
        fun onItemClick(user: User)
        fun onResume()
    }

    interface View : BaseView<Presenter> {
        fun showUsers(users: List<User>)
        fun showRate(rate: Rate)
        fun showProgressIndicator(visible: Boolean)
        fun openUserDetail(user: User)
        fun showError(error: String)
    }
}