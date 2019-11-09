package com.tamimattafi.mvp.database.async

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.MvpBaseContract.Async
import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.database.BaseDao

class ItemAsync<T>(callback: NotificationCallback<T>, private val dao: BaseDao<T>) : Async<SupportSQLiteQuery, T>(callback) {

    override fun doWork(param: SupportSQLiteQuery): T = dao.getItem(param)

}