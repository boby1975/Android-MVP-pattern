package boby.mvp_pattern.data.dataBase.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "userRepos")
data class DBRepo(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val userId: Int,

    @ColumnInfo(name = "rowData")
    val rowData: String
)
