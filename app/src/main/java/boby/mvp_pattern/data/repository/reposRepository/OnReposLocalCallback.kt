package boby.mvp_pattern.data.repository.reposRepository

import boby.mvp_pattern.data.domainModels.Repo

interface OnReposLocalCallback {
    fun onReposLocalReady(repos: List<Repo>)
}