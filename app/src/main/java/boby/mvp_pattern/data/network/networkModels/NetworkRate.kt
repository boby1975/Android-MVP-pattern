package boby.mvp_pattern.data.network.networkModels

import com.google.gson.annotations.SerializedName

data class NetworkRate (
    @SerializedName("limit")
    var limit: Int,
    @SerializedName("remaining")
    var remaining: Int,
    @SerializedName("used")
    var used: Int
)