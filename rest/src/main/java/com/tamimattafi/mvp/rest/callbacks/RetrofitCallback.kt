package com.tamimattafi.mvp.rest.callbacks

import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.core.callbacks.CallbackError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Extended class of retrofit2.Callback class that makes it more functional
 *
 * @see retrofit2.Callback for more information
 *
 * @param notifier: the notifier that will handle and notify errors
 * @param onSuccess: This will be invoked when the response is successful giving the data-source more
 * freedom to process its data and not directly handled by the notifier
 *
 */
open class RetrofitCallback<T, R>(protected open var notifier: ICallbackNotifier<R>?, protected open var onSuccess: ((response: Response<T>?) -> Unit)?) : Callback<T> {



    /**
     * Ordinary retrofit2.Callback's onFailure method
     * If the call fails to execute due to client errors this will be invoked
     *
     * @see retrofit2.Callback for more information
     *
     */
    override fun onFailure(call: Call<T>, throwable: Throwable) {
        notifier?.notifyFailure(CallbackError(throwable.message, throwable.localizedMessage))
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
        if (response.isSuccessful) onSuccess?.invoke(response)
        else notifier?.notifyFailure(
            CallbackError(response.message(), response.message(), response.code())
        )
    }


    /**
     * When this method is called it prevents the call-back from notifying other listeners
     * The call itself isn't canceled here and should be canceled somewhere else
     *
     * @see ICallbackNotifier and
     * @see retrofit2.Call for more information
     *
     */
    fun cancel() {
        notifier = null
        onSuccess = null
    }

}