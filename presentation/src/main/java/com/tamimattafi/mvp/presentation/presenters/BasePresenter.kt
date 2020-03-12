package com.tamimattafi.mvp.presentation.presenters

import androidx.annotation.CallSuper
import com.tamimattafi.mvp.presentation.IPresentationContract.*
import com.tamimattafi.mvp.core.utils.SyntaxUtils.tryCall
import com.tamimattafi.mvp.presentation.IPresentationContract
import com.tamimattafi.mvp.model.IModelContract.*

/**
 * Abstract implementation of IPresenter<V> that must be extended by all other presenters
 *
 * @see IPresenter for more information
 *
 * @param viewController: An instance of the ViewController passed as an interface
 * @param dataSource: An instance of the data-source passed as an interface
 *
 * <V>: View controller type that implements IViewController
 * <D>: Data source type that implements IDataSource
 *
 * @see IViewController and
 * @see IDataSource for more information
 *
 */
abstract class BasePresenter<V: IPresentationContract.IViewController, D: IDataSource>(protected var viewController: V?, protected val dataSource: D) : IPresenter<V> {


    /**
     * Used as a tag for logs to print errors caught by tryCatch method
     *
     * @see tryCall for more information
     *
     */
    protected open var TAG = javaClass.simpleName


    /**
     * Determines whether the presenter can make calls to the view or not
     *
     * @see tryCall for more information
     *
     */
    protected var isViewAvailable: Boolean = false


    /**
     * Super call of this method is mandatory
     *
     * @see IPresenter.onDestroyView for more information
     *
     */
    @CallSuper
    override fun onDestroyView() {
        isViewAvailable = false
    }


    /**
     * Super call of this method is mandatory
     *
     * @see IPresenter.onPause for more information
     *
     */
    @CallSuper
    override fun onPause() {
        isViewAvailable = false
    }


    /**
     * Super call of this method is mandatory
     *
     * @see IPresenter.onResume for more information
     *
     */
    @CallSuper
    override fun onResume() {
        isViewAvailable = true
    }


    /**
     * Super call of this method is mandatory
     *
     * @see IPresenter.onDestroy for more information
     *
     */
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
     * Any throws will be caught and an error log will be printed with the tag 'TAG'
     * @see TAG for more information
     *
     */
    protected fun V?.tryCall(action: V.() -> Unit) = tryCall(isViewAvailable, TAG, action)

}