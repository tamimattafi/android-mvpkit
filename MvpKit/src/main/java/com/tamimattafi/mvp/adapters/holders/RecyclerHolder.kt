package com.tamimattafi.mvp.adapters.holders

import android.view.View
import com.tamimattafi.mvp.MvpBaseContract

open class RecyclerHolder(itemView: View) : ListenerHolder(itemView),
    MvpBaseContract.Holder {
    override var listPosition: Int = -1
}