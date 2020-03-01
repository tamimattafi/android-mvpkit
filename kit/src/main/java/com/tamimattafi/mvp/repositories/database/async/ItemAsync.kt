package com.tamimattafi.mvp.repositories.database.async

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.repositories.database.BaseDao
import com.tamimattafi.mvp.core.threads.SimpleAsync

class ItemAsync<T>(callback: NotificationCallback<T>, private val dao: BaseDao<T>) : com.tamimattafi.mvp.core.threads.SimpleAsync<SupportSQLiteQuery, T>(callback) {

    override fun doWork(param: SupportSQLiteQuery): T = dao.getItem(param)

}