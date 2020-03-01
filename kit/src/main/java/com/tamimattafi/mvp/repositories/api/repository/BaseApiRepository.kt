package com.tamimattafi.mvp.repositories.api.repository

import com.google.gson.Gson
import com.tamimattafi.mvp.core.Callback
import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.repositories.api.callback.RetrofitCallback
import com.tamimattafi.mvp.repositories.global.BaseRepository
import com.tamimattafi.mvp.repositories.global.RepositoryConstants
import okhttp3.ResponseBody
import retrofit2.Call

open class BaseApiRepository : BaseRepository() {

    private val calls: ArrayList<Pair<RetrofitCallback<*, *>, Call<*>>> = ArrayList()

    fun handleActionCall(call: Call<Void>): com.tamimattafi.mvp.core.Callback<Void?>
            = createCallback { notification ->
                call.handleCallback(notification) {
                    notification.notifySuccess(null)
                }
            }

    fun <T, R> handleCustomBodyCall(call: Call<T>, action: (notification: NotificationCallback<R>, data: T) -> Unit): com.tamimattafi.mvp.core.Callback<R>
            = createCallback { notification ->
                call.handleCallback(notification) { body ->

                    body?.let {
                        action(notification, it)
                    } ?: notification.notifyFailure(RepositoryConstants.NULL_BODY_ERROR)

                }
            }

    fun <T> handleBodyCall(call: Call<T>): com.tamimattafi.mvp.core.Callback<T>
            = handleCustomBodyCall(call) { notification, data ->
                notification.notifySuccess(data)
            }


    fun <T> handleResponseBody(call: Call<ResponseBody>, clazz: Class<T>): com.tamimattafi.mvp.core.Callback<T> =
        handleCustomBodyCall(call) { notification, data ->
            notification.notifySuccess(
                Gson().fromJson(data.string(), clazz)
            )
        }


    private fun <T, R> Call<T>.handleCallback(notification: NotificationCallback<R>, onSuccess: (data: T?) -> Unit): RetrofitCallback<T, R>
            = RetrofitCallback<T, R>(notification, onSuccess).also {

                val element = Pair(it, this)
                calls.add(element)
                it.addOnCompleteListener {
                    calls.remove(element)
                }

                this.enqueue(it)

                }

    override fun release() {
        super.release()

        calls.forEach {
            with(it) {
                first.cancel()
                second.cancel()
            }
        }

        calls.clear()
    }

}