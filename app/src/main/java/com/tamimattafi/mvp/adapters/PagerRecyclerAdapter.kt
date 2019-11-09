package com.tamimattafi.mvp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tamimattafi.mvp.MvpBaseContract.*

abstract class PagerRecyclerAdapter<H : Holder>(view: ListenerView<H>) : RecyclerAdapter<H, PagerAdapter>(view), PagerAdapter {

    abstract fun getLoadingErrorHolder(): ViewHolder
    abstract fun getLoadingMoreHolder(): ViewHolder

    override var hasPagingError: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int
        = when {
            getLoadingMoreCondition(position) -> TYPE_LOADING_MORE
            getLoadingErrorCondition(position) -> TYPE_LOADING_ERROR
            else -> super.getItemViewType(position)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = when (viewType) {
            TYPE_LOADING_MORE -> getLoadingMoreHolder()
            TYPE_LOADING_ERROR -> getLoadingErrorHolder()
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemCount(): Int
        = if (isLoading || hasPagingError) dataCount + 1 else super.getItemCount()

    open fun getLoadingMoreCondition(position: Int) = dataCount > 0 && isLoading && position == dataCount

    open fun getLoadingErrorCondition(position: Int) = dataCount > 0 && hasPagingError && position == dataCount

    companion object {
        const val TYPE_LOADING_ERROR = 3
        const val TYPE_LOADING_MORE = 4
    }


}