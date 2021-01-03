package boby.mvp_pattern.data.repository.rateLimitRepository

import android.content.SharedPreferences
import android.os.Handler
import boby.mvp_pattern.data.domainModels.Rate
import com.google.gson.Gson
import javax.inject.Inject


class RateLimitLocalDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val testMode = false
    private val RATE = "rate"

    fun getRate(onRateLimitLocalCallback: OnRateLimitLocalCallback) {
        if (testMode) {
            val rate = Rate(50, 15, 35)
            Handler().postDelayed({ onRateLimitLocalCallback.onRateLimitLocalReady(rate) }, 100)
        } else {
            val jsonRate = sharedPreferences.getString(RATE, "")
            val rate = Gson().fromJson(jsonRate, Rate::class.java)?: Rate()
            onRateLimitLocalCallback.onRateLimitLocalReady(rate)
        }
    }

    fun saveRate(rate: Rate){
        //todo save rate in DB
        val editor = sharedPreferences.edit()
        val jsonRate = Gson().toJson(rate)
        editor.putString(RATE, jsonRate)
        editor.apply()
    }
}