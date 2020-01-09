package com.tamimattafi.mvp.adapters.recycler.holders.bindable

import android.util.Log
import android.view.View

open class SimpleHolder(view: View) : ListenerHolder(view) {

    protected fun invokeClick() {
        onClick?.invoke() ?: Log.d(this.javaClass.simpleName, "onClick listener is null")
    }

    protected fun invokeAction(action: Int) {
        onAction?.invoke(action) ?: Log.d(this.javaClass.simpleName, "onAction listener is null\n tried to invoke action: $action")
    }

}