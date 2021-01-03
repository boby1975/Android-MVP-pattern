package boby.mvp_pattern.dagger

import boby.mvp_pattern.presenter.MainPresenter
import boby.mvp_pattern.presenter.UserReposPresenter
import boby.mvp_pattern.view.main.MainActivity
import boby.mvp_pattern.view.userRepos.UserReposActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MainModule::class])
@Singleton
interface MainComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(userReposActivity: UserReposActivity)
    fun inject(userReposPresenter: UserReposPresenter)
}