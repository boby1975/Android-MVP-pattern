package boby.mvp_pattern.data.dataBase.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class DBUser(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val userId: Int,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = null
)
