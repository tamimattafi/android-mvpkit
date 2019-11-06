package com.tamimattafi.mvp.adapters.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tamimattafi.mvp.MvpBaseContract

open class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    MvpBaseContract.Holder {


    protected var onAction: ((action: Int) -> Unit)? = null
    override var listPosition: Int = -1

    override fun setHolderClickListener(onClick: () -> Unit): MvpBaseContract.Holder = this.also {
        itemView.setOnClickListener {
            onClick.invoke()
        }
    }

    override fun setHolderActionListener(onAction: (action: Int) -> Unit): MvpBaseContract.Holder =
        this.also { it.onAction = onAction }


}