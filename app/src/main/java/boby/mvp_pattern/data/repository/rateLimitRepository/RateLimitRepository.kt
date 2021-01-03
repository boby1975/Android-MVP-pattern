package boby.mvp_pattern.data.repository.rateLimitRepository

interface RateLimitRepository {
    fun getRate(onRateLimitCallback: OnRateLimitCallback)
}