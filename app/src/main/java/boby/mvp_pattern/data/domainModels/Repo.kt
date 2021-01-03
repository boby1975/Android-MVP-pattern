package boby.mvp_pattern.data.domainModels

import com.google.gson.annotations.SerializedName

data class Repo(
    var id: Long = 0,
    var name: String = "",
    var description: String? = null,
    var forks: Int = 0,
    var watchers: Int = 0,
    var stars: Int = 0
)
