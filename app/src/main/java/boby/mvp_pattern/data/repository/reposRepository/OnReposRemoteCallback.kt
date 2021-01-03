package boby.mvp_pattern.data.repository.reposRepository

import boby.mvp_pattern.data.domainModels.Repo

interface OnReposRemoteCallback {
    fun onReposRemoteReady(repos: List<Repo>)
    fun onReposRemoteError(error: String)
}