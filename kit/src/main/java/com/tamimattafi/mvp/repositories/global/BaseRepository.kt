package com.tamimattafi.mvp.repositories.global

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.repositories.callbacks.ActionCallback


abstract class BaseRepository : DataSource {

    private val callbacks: ArrayList<Callback<*>> by lazy { ArrayList<Callback<*>>() }

    override fun release() {
        callbacks.forEach {
            it.cancel()
        }
        callbacks.clear()
    }

    fun <T> createCallback(action: (callback: NotificationCallback<T>) -> Unit): Callback<T> =
        ActionCallback<T>().apply {

            setAction(action)
            callbacks.add(this)

            addCompleteListener {
                callbacks.remove(this)
            }
        }

}