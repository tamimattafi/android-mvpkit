package com.tamimattafi.mvp.adapters.recycler.holders.bindable

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.mvp.MvpBaseContract

open class ListenerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MvpBaseContract.ListenerHolder {

    protected var onAction: ((action: Int) -> Unit)? = null
    protected var onClick: (() -> Unit)? = null

    override fun setHolderClickListener(onClick: () -> Unit): MvpBaseContract.ListenerHolder
            = this.also { it.onClick = onClick }

    override fun setHolderActionListener(onAction: (action: Int) -> Unit): MvpBaseContract.ListenerHolder
            = this.also { it.onAction = onAction }
}