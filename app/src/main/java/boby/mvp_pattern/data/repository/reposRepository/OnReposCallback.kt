package boby.mvp_pattern.data.repository.reposRepository

import boby.mvp_pattern.data.domainModels.Repo

interface OnReposCallback {
    fun onReposReady(repos: List<Repo>)
    fun onReposError(error: String)
}