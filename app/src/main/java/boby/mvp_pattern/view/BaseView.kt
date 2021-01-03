package boby.mvp_pattern.view

import android.content.Context

interface BaseView<P> {
    fun getContext(): Context
}