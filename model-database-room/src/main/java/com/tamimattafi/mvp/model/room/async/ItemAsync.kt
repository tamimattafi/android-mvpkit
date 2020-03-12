package com.tamimattafi.mvp.model.room.async

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.ICoreContract.ICallbackNotifier
import com.tamimattafi.mvp.core.threads.SimpleAsync
import com.tamimattafi.mvp.model.room.IRoomContract.IRoomDao

class ItemAsync<T>(callback: ICallbackNotifier<T>, private val dao: IRoomDao<T>) : SimpleAsync<SupportSQLiteQuery, T>(callback) {

    override fun doWork(param: SupportSQLiteQuery): T = dao.getItem(param)

}