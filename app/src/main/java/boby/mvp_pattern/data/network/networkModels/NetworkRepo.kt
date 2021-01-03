package boby.mvp_pattern.data.network.networkModels

import com.google.gson.annotations.SerializedName

data class NetworkRepo(
    @SerializedName("id")
    var id: Long,
    @SerializedName("node_id")
    var node_id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("full_name")
    var full_name: String,
    @SerializedName("private")
    var private: Boolean,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("forks")
    var forks: Int = 0,
    @SerializedName("watchers")
    var watchers: Int = 0,
    @SerializedName("stargazers_count")
    var stars: Int = 0
)
