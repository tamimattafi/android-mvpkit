package com.tamimattafi.mvp.threads

import android.os.AsyncTask
import android.util.Log
import com.tamimattafi.mvp.MvpBaseContract

abstract class Async<PARAM, RESULT>(private val callback: MvpBaseContract.NotificationCallback<RESULT>) :
    AsyncTask<PARAM, Int, RESULT>() {

    abstract fun doWork(param: PARAM): RESULT

    override fun doInBackground(vararg params: PARAM): RESULT? = try {
        doWork(params[0])
    } catch (e: Exception) {
        Log.e(TAG, e.toString())
        e.printStackTrace()
        null
    }

    override fun onPostExecute(result: RESULT?) {
        super.onPostExecute(result)
        with(callback) {
            result?.let {
                notifySuccess(it)
            } ?: notifyFailure(ERROR)
        }
    }

    companion object {
        const val TAG = "Async"
        const val ERROR = "Something went wrong, check log for tag '$TAG' for more info."
    }

}