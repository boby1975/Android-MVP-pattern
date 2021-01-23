package boby.mvp_pattern.data.dataBase.dbDAOs

import androidx.room.*
import boby.mvp_pattern.data.dataBase.dbModels.DBRepo

@Dao
interface RepoDao: BaseDao<DBRepo> {
    @Query("SELECT * FROM userRepos")
    fun getAll(): List<DBRepo>

    @Query("SELECT * FROM userRepos WHERE userId IN (:userIds)")
    fun getAllByIds(userIds: IntArray): List<DBRepo>

    @Query("SELECT * FROM userRepos WHERE userId = :userId")
    fun getById(userId: Int): DBRepo
}