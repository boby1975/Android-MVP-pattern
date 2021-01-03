package boby.mvp_pattern.data.network.networkModels.responses

import boby.mvp_pattern.data.network.networkModels.NetworkRate

data class RateLimitResponse(
    val rate: NetworkRate
)
