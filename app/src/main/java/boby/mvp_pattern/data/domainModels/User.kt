package boby.mvp_pattern.data.domainModels

import androidx.annotation.Keep

@Keep
data class User (
    var userId: Int = -1,
    var login: String = "",
    var avatarUrl: String? = null
)