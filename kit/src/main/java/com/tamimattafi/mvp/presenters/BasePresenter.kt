package com.tamimattafi.mvp.presenters

import com.tamimattafi.mvp.MvpBaseContract.*

import com.tamimattafi.mvp.utils.SyntaxUtils.tryCall

abstract class BasePresenter<V : View, D : DataSource>(
    protected var view: V?,
    protected val dataSource: D
) : Presenter {

    private var isViewDestroyed: Boolean = false

    override fun onDestroyView() {
        isViewDestroyed = true
    }

    override fun onResume() {
        isViewDestroyed = false
    }

    override fun onDestroy() {
        dataSource.release()
        view = null
    }

    protected fun V?.tryCall(action: V.() -> Unit) = tryCall(action, !isViewDestroyed)

}