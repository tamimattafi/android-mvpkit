package com.tamimattafi.mvp.presentation.lists.adapters.recycler.holders.bindable

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.IListenerHolder

open class ListenerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IListenerHolder {

    protected var onAction: ((action: Int) -> Unit)? = null
    protected var onClick: (() -> Unit)? = null

    override fun setHolderClickListener(onClick: () -> Unit): IListenerHolder
            = this.also { it.onClick = onClick }

    override fun setHolderActionListener(onAction: (action: Int) -> Unit): IListenerHolder
            = this.also { it.onAction = onAction }

    protected fun invokeClick() {
        onClick?.invoke() ?: Log.d(this.javaClass.simpleName, "onClick listener is null")
    }

    protected fun invokeAction(action: Int) {
        onAction?.invoke(action) ?: Log.d(this.javaClass.simpleName, "onAction listener is null\n tried to invoke action: $action")
    }

}