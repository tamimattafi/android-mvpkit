package com.tamimattafi.mvp

import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable


interface MvpBaseContract {

    interface DataSource {
        fun release()
    }

    interface ListDataSource<T> : DataSource {
        fun getDataList(): Callback<ArrayList<T>>
    }

    interface RxListDataSource<T> : DataSource {
        fun getDataList(): Flowable<List<T>>
    }

    interface PagerListDataSource<T> : DataSource {
        fun getDataList(page: Int): Callback<ArrayList<T>>
    }

    interface ActionCallback<T> : Callback<T>, NotificationCallback<T> {
        fun setAction(action: (callback: ActionCallback<T>) -> Unit): Callback<T>
    }

    interface Callback<T> {
        fun addSuccessListener(onSuccess: (data: T) -> Unit): Callback<T>
        fun addFailureListener(onFailure: (message: String) -> Unit): Callback<T>
        fun addCompleteListener(onComplete: () -> Unit): Callback<T>
        fun start()
        fun cancel()
    }

    interface NotificationCallback<T> {
        fun notifySuccess(data: T)
        fun notifyFailure(message: String)
        fun notifyComplete()
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

    interface Adapter {
        var isLoading: Boolean
        fun setTotalDataCount(dataCount: Int)
    }

    interface PagerAdapter : Adapter {
        var hasPagingError: Boolean
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
        fun getList(query: SupportSQLiteQuery): Callback<ArrayList<T>>
        fun getItem(query: SupportSQLiteQuery): Callback<T>
        fun delete(item: T): Callback<Int>
        fun update(item: T): Callback<Int>
        fun insert(item: T): Callback<Long>
        fun insertAll(items: ArrayList<T>): Callback<List<Long>>
    }


}