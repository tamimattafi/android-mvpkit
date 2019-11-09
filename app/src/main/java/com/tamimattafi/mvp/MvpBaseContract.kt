package com.tamimattafi.mvp

import android.os.AsyncTask
import android.util.Log
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable


interface MvpBaseContract {

    interface Repository {
        fun stopListening()
    }

    interface ListRepository<T> : Repository {
        fun getDataList(): Callback<ArrayList<T>>
    }

    interface RxListRepository<T> : Repository {
        fun getDataList(): Flowable<List<T>>
    }

    interface PagerListRepository<T> : Repository {
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
        fun onDestroyView()
    }

    interface RecyclerPresenter<HOLDER : Holder> : Presenter {
        fun bindHolder(holder: HOLDER)
        fun loadData()
        fun refresh()
    }

    interface Holder {
        var listPosition: Int
        fun setHolderClickListener(onClick: () -> Unit): Holder
        fun setHolderActionListener(onAction: (action: Int) -> Unit): Holder
    }

    interface View {
        fun showMessage(message: String)
    }

    interface ListenerView<HOLDER : Holder, ADAPTER: Adapter> : View, AdapterListener<HOLDER> {
        fun bindHolder(holder: HOLDER)
        fun getAdapter(): ADAPTER
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

    interface Database<T> {
        fun getRxList(query: SupportSQLiteQuery): Flowable<List<T>>
        fun getRxItem(query: SupportSQLiteQuery): Flowable<T>
        fun getList(query: SupportSQLiteQuery): Callback<ArrayList<T>>
        fun getItem(query: SupportSQLiteQuery): Callback<T>
        fun delete(item: T): Callback<Int>
        fun update(item: T): Callback<Int>
        fun insert(item: T): Callback<Long>
        fun insertAll(items: ArrayList<T>): Callback<List<Long>>
    }

    abstract class Async<PARAM, RESULT>(private val callback: NotificationCallback<RESULT>) : AsyncTask<PARAM, Int, RESULT>() {

        abstract fun doWork(param: PARAM): RESULT

        override fun doInBackground(vararg params: PARAM): RESULT? = try {
            doWork(params[0])
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            e.printStackTrace()
            null
        }

        override fun onPostExecute(result: RESULT?) {
            super.onPostExecute(result)
            with(callback) {
                result?.let {
                    notifySuccess(it)
                } ?: notifyFailure(ERROR)
            }
        }

        companion object {
            const val TAG = "Async"
            const val ERROR = "Something went wrong, check log for tag '$TAG' for more info."
        }

    }

}