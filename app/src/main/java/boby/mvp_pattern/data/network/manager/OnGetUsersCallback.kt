package boby.mvp_pattern.data.network.manager

import boby.mvp_pattern.data.network.networkModels.NetworkUser

interface OnGetUsersCallback {
    fun onUsersReady(users: List<NetworkUser>)
    fun onUsersError(error: String)
}