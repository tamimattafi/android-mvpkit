package com.tamimattafi.mvp.presentation.lists.adapters.recycler.holders.bindable

import android.view.View
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.IHolder

open class RecyclerHolder(itemView: View) : ListenerHolder(itemView), IHolder {
    override var listPosition: Int = -1
}