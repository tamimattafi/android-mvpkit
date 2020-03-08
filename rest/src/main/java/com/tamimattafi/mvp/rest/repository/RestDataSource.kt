package com.tamimattafi.mvp.rest.repository

import com.google.gson.Gson
import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.core.callbacks.CallbackError
import com.tamimattafi.mvp.core.data.BaseDataSource
import com.tamimattafi.mvp.rest.IRestContract.*
import com.tamimattafi.mvp.rest.callbacks.RetrofitCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

open class RestDataSource : BaseDataSource() {

    private val callBacks: ArrayList<IRetrofitCallback<*, *>> = ArrayList()

    fun handleActionCall(call: Call<Void>): ICallback<Void?>
            = createCallback { notification ->
                call.enqueue(notification) {
                    notification.notifySuccess(null)
                }
            }

    fun <T, R> handleCustomBodyCall(call: Call<T>, action: (notification: ICallbackNotifier<R>, data: T) -> Unit): ICallback<R>
            = createCallback { notification ->
                call.enqueue(notification) { response ->

                    response.body()?.let {
                        action(notification, it)
                    } ?: notification.notifyFailure(CallbackError(NULL_BODY_ERROR, code = response.code()))

                }
            }

    fun <T> handleBodyCall(call: Call<T>): ICallback<T>
            = handleCustomBodyCall(call) { notification, data ->
                notification.notifySuccess(data)
            }


    fun <T> handleResponseBody(call: Call<ResponseBody>, clazz: Class<T>): ICallback<T> =
        handleCustomBodyCall(call) { notification, data ->
            notification.notifySuccess(
                Gson().fromJson(data.string(), clazz)
            )
        }


    private fun <T, R> Call<T>.enqueue(notifier: ICallbackNotifier<R>, onSuccess: (response: Response<T>) -> Unit): RetrofitCallback<T, R>
        = RetrofitCallback<T, R>().also { callback ->

            callBacks.add(callback)

            callback.addSuccessListener(onSuccess)
                .addFailureListener(notifier::notifyFailure)
                .addCompleteListener {
                    callBacks.remove(callback)
                }.start(this)


        }


    override fun release() {
        super.release()

        callBacks.forEach {
            it.cancel()
        }

        callBacks.clear()
    }


    companion object {
        const val NULL_BODY_ERROR = "Expected a body, found null"
    }

}