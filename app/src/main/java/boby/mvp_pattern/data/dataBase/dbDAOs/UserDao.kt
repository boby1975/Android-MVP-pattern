package boby.mvp_pattern.data.dataBase.dbDAOs

import androidx.room.*
import boby.mvp_pattern.data.dataBase.dbModels.DBUser

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<DBUser>

    @Query("SELECT * FROM users WHERE userId IN (:userIds)")
    fun getAllByIds(userIds: IntArray): List<DBUser>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getById(userId: Int): DBUser

    @Insert
    fun insert(user: DBUser)

    @Update
    fun update(user: DBUser)

    @Delete
    fun delete(user: DBUser)
}