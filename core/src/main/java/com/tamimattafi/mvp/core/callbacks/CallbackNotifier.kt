package com.tamimattafi.mvp.core.callbacks

import com.tamimattafi.mvp.core.ICoreContract.*


/**
 * Implementation of ICallBackNotifier<T> where 'T' is the type of result that will be returned on success
 * @see ICallbackNotifier for more information
 *
 * @param callback: The call back that is responsible for storing different listeners
 * @see Callback for more information
 *
 */
open class CallbackNotifier<T>(protected open val callback: Callback<T>) : ICallbackNotifier<T> {


    /**
     * @see ICallbackNotifier.notifySuccess for more information
     *
     */
    @Synchronized
    override fun notifySuccess(data: T) {
        callback.successSequence.forEach {
            it.invoke(data)
        }

        notifyComplete()
    }


    /**
     * @see ICallbackNotifier.notifyFailure for more information
     *
     */
    @Synchronized
    override fun notifyFailure(error: ICallbackError) {
        callback.failureSequence.forEach {
            it.invoke(error)
        }

        notifyComplete()
    }


    /**
     * @see ICallbackNotifier.notifyComplete for more information
     *
     */
    @Synchronized
    override fun notifyComplete() {
        callback.completeSequence.forEach {
            it.invoke()
        }

    }


    /**
     * @see ICallbackNotifier.notifyProgress for more information
     *
     */
    @Synchronized
    override fun notifyProgress(progress: Int) {
        callback.progressSequence.forEach {
            it.invoke(progress)
        }
    }

    /**
     * @see ICallbackNotifier.notifyCancel for more information
     *
     */
    @Synchronized
    override fun notifyCancel() {
        callback.cancelSequence.forEach {
            it.invoke()
        }
    }


    /**
     * @see ICallbackNotifier.notifyStart for more information
     *
     */
    @Synchronized
    override fun notifyStart() {
        callback.startSequence.forEach {
            it.invoke()
        }
    }



}