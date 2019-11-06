package com.tamimattafi.mvp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tamimattafi.mvp.MvpBaseContract.*

abstract class RecyclerAdapter<H : Holder>(private val view: ListenerView<H>) :
    RecyclerView.Adapter<ViewHolder>(), Adapter {

    override var isLoading: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var dataCount: Int = 0

    abstract fun getEmptyHolder(parent: ViewGroup): ViewHolder
    abstract fun getItemHolder(parent: ViewGroup): ViewHolder
    abstract fun getLoadingHolder(parent: ViewGroup): ViewHolder

    override fun setDataCount(dataCount: Int) {
        this.dataCount = dataCount
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            TYPE_LOADING -> getLoadingHolder(parent)
            TYPE_ITEM -> getItemHolder(parent)
            TYPE_EMPTY -> getEmptyHolder(parent)
            else -> throw IllegalArgumentException("${AdapterConstants.VIEW_TYPE_ERROR}: $viewType")
        }

    override fun getItemCount(): Int = if (isLoading || dataCount == 0) 1 else dataCount

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        (holder as? Holder)?.apply {
            listPosition = position

            setHolderActionListener { action ->
                view.onHolderAction(null, action)
            }

        }

        prepareMainHolder(holder as? H ?: return)
    }

    private fun prepareMainHolder(holder: H) {
        with(view) {
            holder.setHolderClickListener {
                onHolderClick(holder)
            }.setHolderActionListener { action ->
                onHolderAction(holder, action)
            }
            bindHolder(holder)
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        getLoadingCondition(position) -> TYPE_LOADING
        getEmptyCondition(position) -> TYPE_EMPTY
        getItemCondition(position) -> TYPE_ITEM
        else -> super.getItemViewType(position)
    }

    open fun getLoadingCondition(position: Int): Boolean =
        position == 0 && dataCount == 0 && isLoading

    open fun getEmptyCondition(position: Int): Boolean = position == 0 && dataCount == 0

    open fun getItemCondition(position: Int): Boolean = position > 0

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
        const val TYPE_EMPTY = 2
    }
}