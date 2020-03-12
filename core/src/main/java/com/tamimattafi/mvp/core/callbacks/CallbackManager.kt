package com.tamimattafi.mvp.core.callbacks

import androidx.annotation.CallSuper
import com.tamimattafi.mvp.core.ICoreContract.*


/**
 * Implementation of ICallbackManager<T> where 'T' is the type of result that will be returned on success
 * @see ICallbackManager for more information
 *
 */
open class CallbackManager<T> : ICallbackManager<T> {


    /**
     * Gets invoked by calling onStart
     *
     * @see setAction and
     * @see Callback.start for more information
     *
     */
    protected open var action: ((callback: ICallbackNotifier<T>) -> Unit)? = null


    /**
     * Holds interactions with the consumer
     *
     * @see ICallback for more information
     *
     */
    protected open val callback: Callback<T> by lazy {
       Callback<T>(this::invokeAction, this::cancelProcessing)
    }


    /**
     * Responsible for notifying call-back listeners in certain events
     *
     * @see ICallbackNotifier for more information
     *
     */
    protected open val notifier: CallbackNotifier<T> by lazy {
        CallbackNotifier(callback)
    }


    /**
     * @see ICallbackManager.setAction for more information
     *
     */
    @CallSuper
    override fun setAction(action: (notifier: ICallbackNotifier<T>) -> Unit): ICallbackManager<T>
        = this.also { it.action = action }


    /**
     * @see ICallbackManager.getCallback for more information
     *
     */
    final override fun getCallback(): ICallback<T> = callback


    /**
     * @see ICallbackManager.getCallbackNotifier for more information
     *
     */
    final override fun getCallbackNotifier(): ICallbackNotifier<T> = notifier


    /**
     * Invokes the action set by setAction
     *
     * @see setAction and
     * @see Callback.start for more info
     *
     */
    @CallSuper
    protected open fun invokeAction() {
        notifier.notifyStart()
        action?.invoke(notifier) ?: notifier.notifyFailure(CallbackError(ACTION_ERROR))
    }


    /**
     * Cancels processing and clears listening sequences
     *
     * @see Callback.cancel for more information
     *
     */
    @CallSuper
    protected open fun cancelProcessing() {
        action = null
        notifier.notifyCancel()
        callback.clearListeners()
    }


    companion object {
        const val ACTION_ERROR = "This call back might be complete or has no action to execute"
    }

}