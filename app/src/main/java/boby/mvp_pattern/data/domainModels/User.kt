package boby.mvp_pattern.data.domainModels

import com.google.gson.annotations.SerializedName

data class User (
    var userId: Int = -1,
    var login: String = "",
    var avatarUrl: String? = null
)