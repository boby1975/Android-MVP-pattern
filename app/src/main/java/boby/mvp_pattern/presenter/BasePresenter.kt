package boby.mvp_pattern.presenter

interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}