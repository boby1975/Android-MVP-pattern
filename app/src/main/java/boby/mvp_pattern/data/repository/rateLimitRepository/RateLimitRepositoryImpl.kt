package boby.mvp_pattern.data.repository.rateLimitRepository

import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.network.NetState
import javax.inject.Inject

class RateLimitRepositoryImpl @Inject constructor(
    private val netState: NetState,
    private val localDataSource: RateLimitLocalDataSource,
    private val remoteDataSource: RateLimitRemoteDataSource): RateLimitRepository {

    override fun getRate(onRateLimitCallback: OnRateLimitCallback) {
        netState.isConnectedToInternet?.let {
            if (it) {
                remoteDataSource.getRate(object : OnRateLimitRemoteCallback {
                    override fun onRateLimitRemoteReady(rate: Rate) {
                        localDataSource.saveRate(rate)
                        onRateLimitCallback.onRateLimitReady(rate)
                    }
                })
            } else {
                localDataSource.getRate(object : OnRateLimitLocalCallback {
                    override fun onRateLimitLocalReady(rate: Rate) {
                        onRateLimitCallback.onRateLimitReady(rate)
                    }
                })
            }
        }
    }

}