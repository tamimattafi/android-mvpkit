package com.tamimattafi.mvp.callbacks

import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.MvpBaseContract.Callback

class ActionCallback<T> : MvpBaseContract.ActionCallback<T> {

    private var onSuccess: ArrayList<(data: T) -> Unit> = ArrayList()
    private var onFailure: ArrayList<(message: String) -> Unit> = ArrayList()
    private var onComplete: ArrayList<() -> Unit> = ArrayList()

    private var action: ((callback: MvpBaseContract.ActionCallback<T>) -> Unit)? = null

    override fun addSuccessListener(onSuccess: (data: T) -> Unit): Callback<T> =
        this.also { it.onSuccess.add(onSuccess) }

    override fun addFailureListener(onFailure: (message: String) -> Unit): Callback<T> =
        this.also { it.onFailure.add(onFailure) }

    override fun addCompleteListener(onComplete: () -> Unit): Callback<T> =
        this.also { it.onComplete.add(onComplete) }

    override fun setAction(action: (callback: MvpBaseContract.ActionCallback<T>) -> Unit): Callback<T> =
        this.also { it.action = action }

    override fun start() {
        action?.invoke(this)
            ?: onFailure.forEach { it.invoke(CallbackConstants.ACTION_ERROR) }
    }

    override fun notifySuccess(data: T) {
        onSuccess.forEach {
            it.invoke(data)
        }

        notifyComplete()
    }

    override fun notifyFailure(message: String) {
        onFailure.forEach {
            it.invoke(message)
        }

        notifyComplete()
    }

    override fun notifyComplete() {
        onComplete.forEach {
            it.invoke()
        }

        cancel()
    }

    override fun cancel() {
        onComplete.clear()
        onFailure.clear()
        onSuccess.clear()
        action = null
    }

}