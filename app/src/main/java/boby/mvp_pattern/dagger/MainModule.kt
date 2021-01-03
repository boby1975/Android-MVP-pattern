package boby.mvp_pattern.dagger

import android.content.Context
import android.content.SharedPreferences
import boby.mvp_pattern.BuildConfig
import boby.mvp_pattern.contract.MainContract
import boby.mvp_pattern.contract.UserReposContract
import boby.mvp_pattern.data.network.NetState
import boby.mvp_pattern.data.network.GithubApi
import boby.mvp_pattern.data.network.manager.NetworkManager
import boby.mvp_pattern.data.network.manager.NetworkManagerImpl
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitLocalDataSource
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitRemoteDataSource
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitRepository
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitRepositoryImpl
import boby.mvp_pattern.data.repository.reposRepository.ReposRepository
import boby.mvp_pattern.data.repository.reposRepository.ReposLocalDataSource
import boby.mvp_pattern.data.repository.reposRepository.ReposRemoteDataSource
import boby.mvp_pattern.data.repository.usersRepository.UsersLocalDataSource
import boby.mvp_pattern.data.repository.usersRepository.UsersRemoteDataSource
import boby.mvp_pattern.data.repository.usersRepository.UsersRepository
import boby.mvp_pattern.data.repository.usersRepository.UsersRepositoryImpl
import boby.mvp_pattern.presenter.MainPresenter
import boby.mvp_pattern.presenter.UserReposPresenter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {
    private val GITHUB_BASE_URL = "https://api.github.com"
    private val SHARED_PREFERENCES_FILE_NAME = "myAppPreferences"

    @Provides
    @Singleton
    fun provideNetState(): NetState {
        return NetState(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideUserReposPresenter(reposRepository: ReposRepository): UserReposContract.Presenter {
        return UserReposPresenter(reposRepository)
    }

    //ReposRepository
    @Provides
    @Singleton
    fun provideReposRepository(netState: NetState,
                               localDataSource: ReposLocalDataSource,
                               remoteDataSource: ReposRemoteDataSource): ReposRepository {
        return ReposRepository(netState, localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideReposRemoteDataSource(networkManager: NetworkManager): ReposRemoteDataSource {
        return ReposRemoteDataSource(networkManager)
    }

    @Provides
    @Singleton
    fun provideReposLocalDataSource(): ReposLocalDataSource {
        return ReposLocalDataSource()
    }

    //UsersRepository
    @Provides
    @Singleton
    fun provideUsersRepository(netState: NetState,
                               localDataSource: UsersLocalDataSource,
                               remoteDataSource: UsersRemoteDataSource): UsersRepository {
        return UsersRepositoryImpl(netState, localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUsersRemoteDataSource(networkManager: NetworkManager): UsersRemoteDataSource {
        return UsersRemoteDataSource(networkManager)
    }

    @Provides
    @Singleton
    fun provideUsersLocalDataSource(sharedPreferences: SharedPreferences): UsersLocalDataSource {
        return UsersLocalDataSource(sharedPreferences)
    }


    //RateLimitRepository
    @Provides
    @Singleton
    fun provideRateLimitRepository(netState: NetState,
                                   localDataSource: RateLimitLocalDataSource,
                                   remoteDataSource: RateLimitRemoteDataSource): RateLimitRepository {
        return RateLimitRepositoryImpl(netState, localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRateLimitRemoteDataSource(networkManager: NetworkManager): RateLimitRemoteDataSource {
        return RateLimitRemoteDataSource(networkManager)
    }

    @Provides
    @Singleton
    fun provideRateLimitLocalDataSource(sharedPreferences: SharedPreferences): RateLimitLocalDataSource {
        return RateLimitLocalDataSource(sharedPreferences)
    }


    @Provides
    @Singleton
    fun provideMainPresenter(usersRepository: UsersRepository,
                             rateLimitRepository: RateLimitRepository): MainContract.Presenter {
        return MainPresenter(usersRepository, rateLimitRepository)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideNetworkManager(githubApi: GithubApi): NetworkManager {
        return NetworkManagerImpl(githubApi)
    }

    @Provides
    @Singleton
    fun provideNetworkGithubApi(): GithubApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()

        return retrofit.create(GithubApi::class.java)
    }

}