package boby.mvp_pattern.data.repository.reposRepository

import android.os.Handler
import boby.mvp_pattern.data.domainModels.Repo

class ReposLocalDataSource {

    fun getRepos(onReposLocalCallback: OnReposLocalCallback) {
        val repoList = listOf(
            Repo(1, "Test-1 from Local", "Description 1", 1, 1, 1),
            Repo(2, "Test-2 from Local", "Description 2", 2, 2, 2),
            Repo(3, "Test-3 from Local", "Description 3", 3, 3, 3)
        )
        Handler().postDelayed({ onReposLocalCallback.onReposLocalReady(repoList) }, 100)
    }

    fun saveRepos(repos: List<Repo>){
        //todo save repos in DB
        repos.let{

        }
    }
}

