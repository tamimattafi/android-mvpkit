package com.tamimattafi.mvp.adapters.recycler.holders.bindable

import android.util.Log
import android.view.View

open class SimpleHolder(view: View) : RecyclerHolder(view) {

    protected fun invokeClick() {
        onClick?.invoke() ?: Log.d(this.javaClass.simpleName, "onClick listener for list position $listPosition is null")
    }

    protected fun invokeAction(action: Int) {
        onAction?.invoke(action) ?: Log.d(this.javaClass.simpleName, "onAction listener for list position $listPosition is null\n tried to invoke action: $action")
    }

}