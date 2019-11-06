package com.tamimattafi.mvp.api.callback

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.api.interactor.InteractorConstants
import com.tamimattafi.mvp.repositories.RepositoryConstants

class ApiCallback<T, R>(
    private var notification: MvpBaseContract.NotificationCallback<R>?,
    private var onSuccess: ((response: T?) -> Unit)?
) : Callback<T> {

    private var onComplete: ArrayList<() -> Unit> = ArrayList()

    override fun onFailure(call: Call<T>, t: Throwable) {
        notification?.notifyFailure(
            t.localizedMessage ?: t.message ?: RepositoryConstants.UNKNOWN_ERROR
        )
        t.printStackTrace()

        notifyComplete()
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        with(response) {
            if (code() == InteractorConstants.CODE_OK) {
                onSuccess?.invoke(response.body())
            } else {
                val error = Gson().fromJson<Error>(
                    response.errorBody()?.string(),
                    Error::class.java
                )?.message
                    ?: InteractorConstants.getCodeMessage(code())
                notification?.notifyFailure(error)
            }
        }

        notifyComplete()
    }

    fun addOnCompleteListener(onComplete: () -> Unit): ApiCallback<T, R> =
        this.also { it.onComplete.add(onComplete) }


    fun cancel() {
        notification = null
        onSuccess = null
        onComplete.clear()
    }

    private fun notifyComplete() {
        onComplete.forEach {
            it.invoke()
        }

        onComplete.clear()
    }

}