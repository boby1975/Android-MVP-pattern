package boby.mvp_pattern

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.repository.rateLimitRepository.OnRateLimitLocalCallback
import boby.mvp_pattern.data.repository.rateLimitRepository.RateLimitLocalDataSource
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("boby.mvp_pattern", appContext.packageName)
    }

    @Test
    fun testRateLimitLocalDataSource() {
        val rateToSave = Rate(100, 35, 75)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPreferences = appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
        val rateLimitLocalDataSource = RateLimitLocalDataSource(sharedPreferences)

        rateLimitLocalDataSource.saveRate(rateToSave)
        rateLimitLocalDataSource.getRate(object : OnRateLimitLocalCallback {
            override fun onRateLimitLocalReady(rate: Rate) {
                assertEquals(rate, rateToSave)
            }
        })
    }

}