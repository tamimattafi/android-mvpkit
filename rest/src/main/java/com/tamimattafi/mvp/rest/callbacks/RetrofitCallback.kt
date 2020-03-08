package com.tamimattafi.mvp.rest.callbacks

import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.core.callbacks.CallbackError
import com.tamimattafi.mvp.rest.IRestContract.IRetrofitCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Extended class of retrofit2.Callback class that makes it more functional
 *
 * @see retrofit2.Callback for more information
 *
 */
open class RetrofitCallback<T, R> : Callback<T>, IRetrofitCallback<T, R> {


    /**
     * The executed call that was assigned on start
     *
     * @see start for more information
     *
     */
    protected open var call: Call<T>? = null



    /**
     * Stores all success listeners attached by addSuccessListener method
     * @see addSuccessListener for more information
     *
     */
    open val successSequence by lazy { ArrayList<(response: Response<T>) -> Unit>() }



    /**
     * Stores all failure listeners attached by addFailureListener method
     * @see addFailureListener for more information
     *
     */
    open val failureSequence by lazy { ArrayList<(error: ICallbackError) -> Unit>() }



    /**
     * Stores all complete listeners attached by addCompleteListener method
     * @see addCompleteListener for more information
     *
     */
    open val completeSequence by lazy { ArrayList<() -> Unit>() }



    /**
     * Ordinary retrofit2.Callback's onFailure method
     * If the call fails to execute due to client errors this will be invoked
     *
     * @see retrofit2.Callback for more information
     *
     */
    override fun onFailure(call: Call<T>, throwable: Throwable) {
        notifyFailure(CallbackError(throwable.message, throwable.localizedMessage))
        throwable.printStackTrace()
    }



    /**
     * Ordinary retrofit2.Callback's onResponse method
     * If the call fails due to server errors this will be invoked call-back failure listeners will be invoked
     * otherwise, onSuccess lambda will be invoked
     *
     * @see ICallbackNotifier and
     * @see retrofit2.Callback for more information
     *
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) notifySuccess(response)
        else notifyFailure(CallbackError(response.message(), response.message(), response.code()))
    }



    /**
     * @see IRetrofitCallback.addSuccessListener for more information
     *
     */
    @Synchronized
    override fun addSuccessListener(onSuccess: (response: Response<T>) -> Unit): IRetrofitCallback<T, R> =
        this.also { it.successSequence.add(onSuccess) }



    /**
     * @see IRetrofitCallback.addFailureListener for more information
     *
     */
    @Synchronized
    override fun addFailureListener(onFailure: (error: ICallbackError) -> Unit): IRetrofitCallback<T, R> =
        this.also { it.failureSequence.add(onFailure) }



    /**
     * @see IRetrofitCallback.addCompleteListener for more information
     *
     */
    @Synchronized
    override fun addCompleteListener(onComplete: () -> Unit): IRetrofitCallback<T, R>
            = this.also { it.completeSequence.add(onComplete) }



    /**
     * Invokes all failure listeners in failure sequence
     *
     * @see failureSequence for more information
     *
     * @param error: the error to be returned to the listeners
     * @see ICallbackError for more information
     *
     */
    @Synchronized
    private fun notifyFailure(error: ICallbackError) {
        failureSequence.forEach {
            it.invoke(error)
        }

        notifyComplete()
    }



    /**
     * Invokes all success listeners in success sequence
     *
     * @see successSequence for more information
     *
     * @param response: the response to be returned to the listeners
     * @see Response for more information
     *
     */
    @Synchronized
    private fun notifySuccess(response: Response<T>) {
        successSequence.forEach {
            it.invoke(response)
        }

        notifyComplete()
    }


    /**
     * Invokes all complete listeners in complete sequence
     *
     * @see completeSequence for more information
     *
     * This method will be automatically called if success or failure occurs
     * @see notifySuccess and
     * @see notifyFailure failure for more information
     *
     */
    @Synchronized
    private fun notifyComplete() {
        completeSequence.forEach {
            it.invoke()
        }
    }


    /**
     * @see IRetrofitCallback.start for more information
     *
     */
    override fun start(call: Call<T>) {
        this.call = call
        call.enqueue(this)
    }




    /**
     * @see IRetrofitCallback.cancel for more information
     *
     */
    override fun cancel() {
        successSequence.clear()
        failureSequence.clear()
        completeSequence.clear()
        call?.cancel()
    }

}