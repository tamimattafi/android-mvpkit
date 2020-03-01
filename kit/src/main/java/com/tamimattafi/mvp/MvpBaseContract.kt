package com.tamimattafi.mvp

import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable


interface MvpBaseContract {


    interface ListDataSource<T> : DataSource {
        fun getDataList(): com.tamimattafi.mvp.core.Callback<ArrayList<T>>
    }

    interface RxListDataSource<T> : DataSource {
        fun getDataList(): Flowable<List<T>>
    }

    interface PagerListDataSource<T> : DataSource {
        fun getDataList(page: Int): com.tamimattafi.mvp.core.Callback<ArrayList<T>>
    }

    interface ActionCallback<T> : com.tamimattafi.mvp.core.Callback<T>, NotificationCallback<T> {
        fun setAction(action: (callback: ActionCallback<T>) -> Unit): com.tamimattafi.mvp.core.Callback<T>
    }

    interface Presenter {
        fun onResume()
        fun onDestroyView()
        fun onDestroy()
    }

    interface RecyclerPresenter<HOLDER : Holder> : Presenter {
        fun bindHolder(holder: HOLDER)
        fun loadData()
        fun refresh()
    }

    interface Holder : ListenerHolder {
        var listPosition: Int
    }

    interface ListenerHolder {
        fun setHolderClickListener(onClick: () -> Unit): ListenerHolder
        fun setHolderActionListener(onAction: (action: Int) -> Unit): ListenerHolder
    }

    interface View {
        fun showMessage(message: String)
    }

    interface ListenerView<HOLDER : Holder> : View, AdapterListener<HOLDER> {
        fun bindHolder(holder: HOLDER)
        fun getAdapter(): Adapter
    }

    interface PagerListenerView<HOLDER : Holder> : ListenerView<HOLDER> {
        override fun getAdapter(): PagerAdapter
    }

    interface Adapter {
        var isLoading: Boolean
        var hasError: Boolean
        fun setTotalDataCount(dataCount: Int)
        fun notifyChanges()
        fun notifyDelete(listPosition: Int)
        fun notifyChanges(listPosition: Int)
        fun isMainItemHolder(layoutPosition: Int): Boolean
    }

    interface PagerAdapter : Adapter {
        fun addNewData(dataCount: Int)
    }

    interface AdapterListener<HOLDER : Holder> {
        fun onHolderClick(holder: HOLDER)
        fun onHolderAction(holder: HOLDER?, action: Int)
    }

    interface AuthPreferences<T : Token> {
        fun setLoggedIn(boolean: Boolean)
        fun isLoggedIn(): Boolean
        fun setToken(token: T?)
        fun getToken(): T?
    }

    interface Token {
        val value: String
    }

    interface DatabaseRepository<T> : DataSource {
        fun getRxList(query: SupportSQLiteQuery): Flowable<List<T>>
        fun getRxItem(query: SupportSQLiteQuery): Flowable<T>
        fun getList(query: SupportSQLiteQuery): com.tamimattafi.mvp.core.Callback<ArrayList<T>>
        fun getItem(query: SupportSQLiteQuery): com.tamimattafi.mvp.core.Callback<T>
        fun delete(item: T): com.tamimattafi.mvp.core.Callback<Int>
        fun update(item: T): com.tamimattafi.mvp.core.Callback<Int>
        fun insert(item: T): com.tamimattafi.mvp.core.Callback<Long>
        fun insertAll(items: ArrayList<T>): com.tamimattafi.mvp.core.Callback<List<Long>>
    }


}