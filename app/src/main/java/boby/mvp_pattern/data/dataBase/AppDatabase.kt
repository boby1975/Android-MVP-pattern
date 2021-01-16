package boby.mvp_pattern.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import boby.mvp_pattern.data.dataBase.dbDAOs.RepoDao
import boby.mvp_pattern.data.dataBase.dbDAOs.UserDao
import boby.mvp_pattern.data.dataBase.dbModels.DBRepo
import boby.mvp_pattern.data.dataBase.dbModels.DBUser

@Database(entities = arrayOf(DBRepo::class, DBUser::class), version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun userDao(): UserDao
}