package com.tamimattafi.mvp.model.data

import androidx.annotation.CallSuper
import com.tamimattafi.mvp.model.IModelContract.IDataSource
import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.core.callbacks.CallbackManager


/**
 * Abstract implementation of IDataSource that must be extended by all other data sources
 *
 * @see IDataSource for more information
 *
 */
abstract class BaseDataSource : IDataSource {


    /**
     * Stores all callbacks in order to cancel and clear them after the view is released
     *
     * @see release and
     * @see Callback.cancel for more information
     *
     */
    protected val callbacks: ArrayList<ICallback<*>> by lazy { ArrayList<ICallback<*>>() }


    /**
     * Creates a call-back that can be sent to the consumer and stores it for later usage
     *
     * @see callbacks for more information
     *
     * @param action: The action that will be passed to the callback manager and be trigger
     * when start method is called
     *
     * @see CallbackManager.setAction and
     * @see Callback.start for more information
     *
     * <T>: Result type
     *
     */
    protected fun <T> createCallback(action: (notifier: ICallbackNotifier<T>) -> Unit): ICallback<T> =
        CallbackManager<T>().setAction(action).getCallback().apply { handle() }



    /**
     * Extension method that adds the call-back to the list of call-backs and removes it when it's canceled
     * @see createCallback for more information
     *
     * <T>: Result type
     *
     */
    private fun <T> ICallback<T>.handle() {
        callbacks.add(this)

        addCancelListener {
            synchronized(this) {
                callbacks.remove(this)
            }
        }
    }


    /**
     * Cancels callbacks and clears them
     * Super call of this method is mandatory
     *
     * @see IDataSource.release for more information
     */
    @CallSuper
    override fun release() {
        callbacks.forEach { it.cancel() }
        callbacks.clear()
    }
}