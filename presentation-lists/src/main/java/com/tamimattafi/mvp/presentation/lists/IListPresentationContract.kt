package com.tamimattafi.mvp.presentation.lists

import com.tamimattafi.mvp.presentation.IPresentationContract.*

interface IListPresentationContract {

    interface IRecyclerPresenter<H: IHolder, V: IViewController> : IPresenter<V> {
        fun bindHolder(holder: H)
        fun loadData()
        fun refresh()
    }

    interface IHolder : IListenerHolder {
        var listPosition: Int
    }

    interface IListenerHolder {
        fun setHolderClickListener(onClick: () -> Unit): IListenerHolder
        fun setHolderActionListener(onAction: (action: Int) -> Unit): IListenerHolder
    }

    interface IListenerView<HOLDER : IHolder> : IViewController, IAdapterListener<HOLDER> {
        fun bindHolder(holder: HOLDER)
        fun getAdapter(): IAdapter
    }

    interface IPagerListenerView<HOLDER : IHolder> : IListenerView<HOLDER> {
        override fun getAdapter(): IPagerAdapter
    }

    interface IAdapter {
        var isLoading: Boolean
        var hasError: Boolean
        fun setTotalDataCount(dataCount: Int)
        fun notifyChanges()
        fun notifyDelete(listPosition: Int)
        fun notifyChanges(listPosition: Int)
        fun isMainItemHolder(layoutPosition: Int): Boolean
    }

    interface IPagerAdapter : IAdapter {
        fun addNewData(dataCount: Int)
    }

    interface IAdapterListener<HOLDER : IHolder> {
        fun onHolderClick(holder: HOLDER)
        fun onHolderAction(holder: HOLDER?, action: Int)
    }

}