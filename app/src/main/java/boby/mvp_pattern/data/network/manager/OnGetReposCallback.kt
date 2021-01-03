package boby.mvp_pattern.data.network.manager

import boby.mvp_pattern.data.network.networkModels.NetworkRepo

interface OnGetReposCallback {
    fun onReposReady(repos: List<NetworkRepo>)
    fun onReposError(error: String)
}