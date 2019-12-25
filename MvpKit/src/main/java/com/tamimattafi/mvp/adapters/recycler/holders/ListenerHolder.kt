package com.tamimattafi.mvp.adapters.recycler.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.mvp.MvpBaseContract

open class ListenerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MvpBaseContract.ListenerHolder {

    protected var onAction: ((action: Int) -> Unit)? = null

    override fun setHolderClickListener(onClick: () -> Unit): MvpBaseContract.ListenerHolder = this.also {
        itemView.setOnClickListener {
            onClick.invoke()
        }
    }

    override fun setHolderActionListener(onAction: (action: Int) -> Unit): MvpBaseContract.ListenerHolder =
        this.also { it.onAction = onAction }
}