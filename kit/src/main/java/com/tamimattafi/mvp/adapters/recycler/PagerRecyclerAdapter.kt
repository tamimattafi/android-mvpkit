package com.tamimattafi.mvp.adapters.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tamimattafi.mvp.MvpBaseContract.*

abstract class PagerRecyclerAdapter<H : Holder>(view: ListenerView<H>) : RecyclerAdapter<H>(view),
    PagerAdapter {

    abstract fun getLoadingErrorHolder(parent: ViewGroup): ViewHolder
    abstract fun getLoadingMoreHolder(parent: ViewGroup): ViewHolder

    override fun addNewData(dataCount: Int) {
        if (this.dataCount > 0) {
            val startPosition = this.dataCount - 1
            this.dataCount += dataCount
            notifyItemRangeInserted(startPosition, dataCount)
            notifyItemRangeChanged(startPosition + dataCount, lastAdapterPosition)
        } else {
            this.dataCount = dataCount
            notifyChanges()
        }
    }

    override fun getItemViewType(position: Int): Int
        = when {
            getLoadingMoreCondition(position) -> TYPE_LOADING_MORE
            getLoadingErrorCondition(position) -> TYPE_LOADING_ERROR
            else -> super.getItemViewType(position)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
        = when (viewType) {
            TYPE_LOADING_MORE -> getLoadingMoreHolder(parent)
            TYPE_LOADING_ERROR -> getLoadingErrorHolder(parent)
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemCount(): Int = if (isLoading || hasError) dataCount + 1 else super.getItemCount()

    open fun getLoadingMoreCondition(position: Int) = dataCount > 0 && isLoading && position == dataCount

    open fun getLoadingErrorCondition(position: Int) = dataCount > 0 && hasError && position == dataCount

    companion object {
        const val TYPE_LOADING_ERROR = 4
        const val TYPE_LOADING_MORE = 5
    }


}