package boby.mvp_pattern.data.repository.rateLimitRepository

import android.os.Handler
import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.network.manager.NetworkManager
import boby.mvp_pattern.data.network.manager.OnGetRateLimitCallback
import boby.mvp_pattern.data.network.networkModels.NetworkRate
import org.modelmapper.ModelMapper
import javax.inject.Inject


class RateLimitRemoteDataSource @Inject constructor(private val networkManager: NetworkManager) {
    private val testMode = false

    fun getRate(onRateLimitRemoteCallback: OnRateLimitRemoteCallback) {
        if (testMode) {
            val rate = Rate(100, 35, 75)
            Handler().postDelayed({ onRateLimitRemoteCallback.onRateLimitRemoteReady(rate) }, 2000)
        } else {
            networkManager.getRateLimit(object : OnGetRateLimitCallback {
                override fun onRateLimitReady(rate: NetworkRate) {
                    val modelMapper = ModelMapper()
                    val domainRate: Rate = modelMapper.map(rate, Rate::class.java)
                    domainRate.let {
                        onRateLimitRemoteCallback.onRateLimitRemoteReady(domainRate)
                    }
                }
            })
        }
    }
}