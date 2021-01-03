package boby.mvp_pattern.data.repository.rateLimitRepository

import boby.mvp_pattern.data.domainModels.Rate

interface OnRateLimitLocalCallback {
    fun onRateLimitLocalReady (rate: Rate)
}