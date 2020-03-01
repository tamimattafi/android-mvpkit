package com.tamimattafi.mvp.core.threads

import android.os.AsyncTask
import androidx.annotation.CallSuper
import com.tamimattafi.mvp.core.CoreContract.*
import com.tamimattafi.mvp.core.utils.SyntaxUtils.TRY_CATCH_TAG
import com.tamimattafi.mvp.core.utils.SyntaxUtils.tryCatch

abstract class SimpleAsync<PARAM, RESULT>(private val notifier: ICallbackNotifier<RESULT>) : AsyncTask<PARAM, Int, RESULT>() {

    /**
     * This method must be overridden in order to handle the of returning the result
     * @param param: the first parameter passed to execute method
     */
    protected abstract fun doWork(param: PARAM): RESULT


    /**
     * This method will be called after the background is finished
     * @see onPostExecute for more information
     *
     * @param result: the result returned by the background work
     */
    protected open fun ICallbackNotifier<RESULT>.onNotifyWorkFinished(result: RESULT?) {
        result?.let { notifySuccess(it) } ?: notifyFailure(ERROR)
    }


    /**
     * This is the ordinary doInBackground method of AsyncTask, the difference is that it is crash-safe
     * by being is surrounded by a try-catch block
     * If the work fails and throws an exception, a null is returned as a result
     *
     * @see doWork for more information
     */
    final override fun doInBackground(vararg params: PARAM): RESULT?
            = tryCatch { doWork(params[0]) }


    /**
     * This is the ordinary doInBackground method of AsyncTask
     * @see onNotifyWorkFinished for more information
     */
    @CallSuper
    override fun onPostExecute(result: RESULT?) {
        super.onPostExecute(result)
        notifier.onNotifyWorkFinished(result)
    }

    companion object {

        /**
         * This message will be printer in your error logs if your program throws while processing background work
         * @see doInBackground for more information
         */
        private const val ERROR = "Something went wrong while processing data in the background. Check error logs for tag '$TRY_CATCH_TAG' for more info"
    }


}