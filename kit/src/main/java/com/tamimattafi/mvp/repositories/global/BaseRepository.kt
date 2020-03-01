package com.tamimattafi.mvp.repositories.global

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.repositories.callbacks.ActionCallback


abstract class BaseRepository : DataSource {

    private val callbacks: ArrayList<com.tamimattafi.mvp.core.Callback<*>> by lazy { ArrayList<com.tamimattafi.mvp.core.Callback<*>>() }

    override fun release() {
        callbacks.forEach {
            it.cancel()
        }
        callbacks.clear()
    }

    fun <T> createCallback(action: (callback: NotificationCallback<T>) -> Unit): com.tamimattafi.mvp.core.Callback<T> =
        ActionCallback<T>().apply {

            setAction(action)
            callbacks.add(this)

            addCompleteListener {
                callbacks.remove(this)
            }
        }

}