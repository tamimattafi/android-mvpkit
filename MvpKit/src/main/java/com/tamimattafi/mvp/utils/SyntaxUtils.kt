package com.tamimattafi.mvp.utils

import android.util.Log

object SyntaxUtils {

    const val TAG = "SyntaxUtils"

    fun <T> tryCatch(action: () -> T): T? = try {
        action.invoke()
    } catch (e: Exception) {
        Log.e(TAG, "tryCatch:" + (e.localizedMessage ?: e.message ?: e.toString()))
        null
    }

    fun <T> T?.tryCall(action: T.() -> Unit, condition: Boolean = true) =
        this?.takeIf { condition }?.let {
            tryCatch { action.invoke(it) }
        }


}