package boby.mvp_pattern.data.domainModels

import androidx.annotation.Keep

@Keep
data class Rate(
    var limit: Int = 0,
    var remaining: Int = 0,
    var used: Int = 0
)
