package com.tamimattafi.mvp.api.repository

import retrofit2.Call
import com.tamimattafi.mvp.MvpBaseContract.Callback
import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.api.callback.ApiCallback
import com.tamimattafi.mvp.repositories.BaseRepository
import com.tamimattafi.mvp.repositories.RepositoryConstants

open class BaseApiRepository : BaseRepository() {

    private val calls: ArrayList<Pair<ApiCallback<*, *>, Call<*>>> = ArrayList()

    fun handleActionCall(call: Call<Void>): Callback<Boolean>
            = createCallback { notification ->
                call.handleCallback(notification) {
                    notification.notifySuccess(true)
                }
            }

    fun <T, R> handleCustomBodyCall(call: Call<T>, action: (notification: NotificationCallback<R>, data: T) -> Unit): Callback<R>
            = createCallback { notification ->
                call.handleCallback(notification) { body ->

                    body?.let {
                        action(notification, it)
                    } ?: notification.notifyFailure(RepositoryConstants.NULL_BODY_ERROR)

                }
            }

    fun <T> handleBodyCall(call: Call<T>): Callback<T>
            = handleCustomBodyCall(call) { notification, data ->
                notification.notifySuccess(data)
            }


    private fun <T, R> Call<T>.handleCallback(notification: NotificationCallback<R>, onSuccess: (data: T?) -> Unit): ApiCallback<T, R>
            = ApiCallback<T, R>(notification, onSuccess).also {

                val element = Pair(it, this)
                calls.add(element)
                it.addOnCompleteListener {
                    calls.remove(element)
                }

                this.enqueue(it)

                }

    override fun stopListening() {
        super.stopListening()

        calls.forEach {
            with(it) {
                first.cancel()
                second.cancel()
            }
        }

        calls.clear()
    }

}