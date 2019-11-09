package com.tamimattafi.mvp.repositories.database.async

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.MvpBaseContract.Async
import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.repositories.database.BaseDao

class ListAsync<T>(callback: NotificationCallback<ArrayList<T>>, private val dao: BaseDao<T>) : Async<SupportSQLiteQuery, ArrayList<T>>(callback) {

    override fun doWork(param: SupportSQLiteQuery): ArrayList<T> = ArrayList(dao.getList(param))

}