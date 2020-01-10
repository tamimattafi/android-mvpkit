package com.tamimattafi.mvp.adapters.recycler.holders.bindable

import android.view.View
import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.adapters.recycler.holders.bindable.ListenerHolder

open class RecyclerHolder(itemView: View) : ListenerHolder(itemView),
    MvpBaseContract.Holder {
    override var listPosition: Int = -1
}