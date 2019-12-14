package com.tamimattafi.mvp.presenters

import com.tamimattafi.mvp.MvpBaseContract.*

import com.tamimattafi.mvp.utils.SyntaxUtils.tryCall

abstract class BasePresenter<V : View, R : Repository>(
    protected var view: V?,
    protected val repository: R
) : Presenter {

    private var isViewDestroyed: Boolean = false

    override fun onDestroyView() {
        isViewDestroyed = true
    }

    override fun onResume() {
        isViewDestroyed = false
    }

    override fun onDestroy() {
        repository.stopListening()
        view = null
    }

    protected fun V?.tryCall(action: V.() -> Unit) = tryCall(action, !isViewDestroyed)

}