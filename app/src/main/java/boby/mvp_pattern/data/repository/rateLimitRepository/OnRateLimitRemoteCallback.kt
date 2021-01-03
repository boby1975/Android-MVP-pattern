package boby.mvp_pattern.data.repository.rateLimitRepository

import boby.mvp_pattern.data.domainModels.Rate

interface OnRateLimitRemoteCallback {
    fun onRateLimitRemoteReady (rate: Rate)
}