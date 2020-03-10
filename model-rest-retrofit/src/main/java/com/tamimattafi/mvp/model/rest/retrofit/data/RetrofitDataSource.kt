package com.tamimattafi.mvp.model.rest.retrofit.data

import androidx.annotation.CallSuper
import com.google.gson.Gson
import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.core.callbacks.CallbackError
import com.tamimattafi.mvp.core.data.BaseDataSource
import com.tamimattafi.mvp.model.rest.retrofit.IRetrofitContract.*
import com.tamimattafi.mvp.model.rest.retrofit.callbacks.RetrofitCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

open class RetrofitDataSource : BaseDataSource(), IRetrofitDataSource {

    private val callBacks: ArrayList<IRetrofitCallback<*, *>> = ArrayList()

    protected fun handleActionCall(call: Call<Void>): ICallback<Void?>
            = createCallback { notification ->
                call.enqueue(notification) {
                    notification.notifySuccess(null)
                }
            }

    protected fun <T, R> handleCustomBodyCall(call: Call<T>, action: (notification: ICallbackNotifier<R>, data: T) -> Unit): ICallback<R>
            = createCallback { notification ->
                call.enqueue(notification) { response ->

                    response.body()?.let {
                        action(notification, it)
                    } ?: notification.notifyFailure(CallbackError(NULL_BODY_ERROR, code = response.code()))

                }
            }

    protected fun <T> handleBodyCall(call: Call<T>): ICallback<T>
            = handleCustomBodyCall(call) { notification, data ->
                notification.notifySuccess(data)
            }


    protected fun <T> handleResponseBody(call: Call<ResponseBody>, clazz: Class<T>): ICallback<T> =
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


    @CallSuper
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