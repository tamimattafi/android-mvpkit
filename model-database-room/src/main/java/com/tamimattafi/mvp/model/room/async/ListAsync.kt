package com.tamimattafi.mvp.model.room.async

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.ICoreContract.ICallbackNotifier
import com.tamimattafi.mvp.core.threads.SimpleAsync
import com.tamimattafi.mvp.model.room.IRoomContract.IRoomDao

class ListAsync<T>(callback: ICallbackNotifier<ArrayList<T>>, private val dao: IRoomDao<T>) : SimpleAsync<SupportSQLiteQuery, ArrayList<T>>(callback) {

    override fun doWork(param: SupportSQLiteQuery): ArrayList<T> = ArrayList(dao.getList(param))

}