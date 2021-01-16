package boby.mvp_pattern.data.repository.reposRepository

import android.os.Handler
import android.util.Log
import boby.mvp_pattern.data.dataBase.AppDatabase
import boby.mvp_pattern.data.dataBase.dbModels.DBRepo
import boby.mvp_pattern.data.domainModels.Repo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import javax.inject.Inject

class ReposLocalDataSource @Inject constructor(private val db: AppDatabase){
    private val LOG_TAG = this.javaClass.name.split(".").last()
    private val testMode = false
    private val repoDao = db.repoDao()

    fun getRepos(onReposLocalCallback: OnReposLocalCallback, userId: Int) {
        if (testMode) {
            val repoList = listOf(
                Repo(1, "Test-1 from Local", "Description 1", 1, 1, 1),
                Repo(2, "Test-2 from Local", "Description 2", 2, 2, 2),
                Repo(3, "Test-3 from Local", "Description 3", 3, 3, 3)
            )
            Handler().postDelayed({ onReposLocalCallback.onReposLocalReady(repoList) }, 100)
        } else {
            //get repos from DB
            GlobalScope.launch(Dispatchers.Main){
                var repoList = listOf<Repo>()
                try {
                    val dbUserRepo = withContext(Dispatchers.IO) { getRepoById(userId) }
                    dbUserRepo?.rowData?.let {
                        val repoListType = object : TypeToken<List<Repo>>() {}.type
                        repoList = Gson().fromJson<List<Repo>>(it, repoListType) ?: listOf()
                    }
                } catch (exception: Exception) {
                    Log.w(LOG_TAG, "$exception handled !!!")
                }
                onReposLocalCallback.onReposLocalReady(repoList)
                Log.d(LOG_TAG, "getRepos done")
            }
        }
    }

    fun saveRepos(repos: List<Repo>, userId: Int) {
        //save repos in DB
        GlobalScope.launch(Dispatchers.Main + handler){
            val userRepo = DBRepo(userId, Gson().toJson(repos))
            val dbUserRepo = withContext(Dispatchers.IO) { getRepoById(userId) }
            if (dbUserRepo == null) {
                withContext(Dispatchers.IO) { repoDao.insert(userRepo) }
            } else {
                withContext(Dispatchers.IO) { repoDao.update(userRepo) }
            }
            Log.d(LOG_TAG, "saveRepos done")
        }
    }

    private suspend fun getRepoById(userId: Int): DBRepo {
        return repoDao.getById(userId)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(LOG_TAG, "$exception handled !")
    }
}

