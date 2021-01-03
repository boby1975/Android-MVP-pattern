package boby.mvp_pattern.data.network.networkModels

import com.google.gson.annotations.SerializedName

data class NetworkUser(
    @SerializedName("id")
    var userId: Int,
    @SerializedName("login")
    var login: String,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)
