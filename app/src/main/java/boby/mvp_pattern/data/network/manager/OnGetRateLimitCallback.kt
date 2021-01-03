package boby.mvp_pattern.data.network.manager

import boby.mvp_pattern.data.network.networkModels.NetworkRate

interface OnGetRateLimitCallback {
    fun onRateLimitReady(rate: NetworkRate)
}