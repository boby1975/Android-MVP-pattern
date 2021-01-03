package boby.mvp_pattern.data.repository.rateLimitRepository

import boby.mvp_pattern.data.domainModels.Rate

interface OnRateLimitCallback {
    fun onRateLimitReady (rate: Rate)
}