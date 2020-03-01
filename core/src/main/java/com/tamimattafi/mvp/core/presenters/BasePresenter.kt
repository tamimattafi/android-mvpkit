package com.tamimattafi.mvp.core.presenters

import androidx.annotation.CallSuper
import com.tamimattafi.mvp.core.CoreContract.*

import com.tamimattafi.mvp.core.utils.SyntaxUtils.tryCall

abstract class BasePresenter<V : IViewController, D : IDataSource>(protected var viewController: V?, protected val dataSource: D) : IPresenter<V> {

    protected open var TAG = javaClass.simpleName
    protected var isViewAvailable: Boolean = false

    @CallSuper
    override fun onDestroyView() {
        isViewAvailable = false
    }

    override fun onPause() {
        isViewAvailable = false
    }

    @CallSuper
    override fun onResume() {
        isViewAvailable = true
    }

    @CallSuper
    override fun onDestroy() {
        dataSource.release()
        viewController = null
    }


    /**
     * This extension is crash-safe and it must be called before any interaction with the IViewController
     * If the IViewController is destroyed or the View is not available the code inside this block will
     * not be triggered
     *
     * Any throw will be caught and an error log will be printed with the tag 'TAG'
     * @see TAG for more information
     * @see  for more information
     *
     */
    protected fun V?.tryCall(action: V.() -> Unit) = tryCall(isViewAvailable, TAG, action)

}