package com.tamimattafi.mvp.adapters.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.adapters.AdapterConstants

abstract class RecyclerAdapter<H : Holder>(private val view: ListenerView<H>) :
    RecyclerView.Adapter<ViewHolder>(), Adapter {

    override var isLoading: Boolean = false
    override var hasError: Boolean = false

    protected var dataCount: Int = 0

    abstract fun getEmptyHolder(parent: ViewGroup): ViewHolder
    abstract fun getItemHolder(parent: ViewGroup): ViewHolder
    abstract fun getLoadingHolder(parent: ViewGroup): ViewHolder
    abstract fun getErrorHolder(parent: ViewGroup): ViewHolder

    override fun setTotalDataCount(dataCount: Int) {
        this.dataCount = dataCount
    }

    override fun notifyChanges() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            TYPE_LOADING -> getLoadingHolder(parent)
            TYPE_ITEM -> getItemHolder(parent)
            TYPE_EMPTY -> getEmptyHolder(parent)
            TYPE_ERROR -> getErrorHolder(parent)
            else -> throw IllegalArgumentException("${AdapterConstants.VIEW_TYPE_ERROR}: $viewType")
        }

    override fun getItemCount(): Int = if (isLoading || hasError || dataCount == 0) 1 else dataCount

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        (holder as? ListenerHolder)?.apply {
            setHolderActionListener { action ->
                view.onHolderAction(null, action)
            }
        }

        (holder as? H)?.apply {
            listPosition = getListPosition(position)
            prepare()
        }

    }

    private fun H.prepare() {
        with(view) {
            setHolderClickListener {
                onHolderClick(this@prepare)
            }.setHolderActionListener { action ->
                onHolderAction(this@prepare, action)
            }
            bindHolder(this@prepare)
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        getLoadingCondition(position) -> TYPE_LOADING
        getErrorCondition(position) -> TYPE_ERROR
        getEmptyCondition(position) -> TYPE_EMPTY
        getItemCondition(position) -> TYPE_ITEM
        else -> super.getItemViewType(position)
    }

    open fun getLoadingCondition(position: Int): Boolean = position == 0 && dataCount == 0 && isLoading

    open fun getEmptyCondition(position: Int): Boolean = position == 0 && dataCount == 0

    open fun getErrorCondition(position: Int): Boolean = getEmptyCondition(position) && hasError

    open fun getItemCondition(position: Int): Boolean = position > 0

    open fun getListPosition(adapterPosition: Int): Int = adapterPosition

    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_LOADING = 1
        const val TYPE_EMPTY = 2
        const val TYPE_ERROR = 3
    }
}