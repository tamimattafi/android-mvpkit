package com.tamimattafi.mvp.model.room.database

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.ICoreContract.ICallback
import com.tamimattafi.mvp.model.data.BaseDataSource
import com.tamimattafi.mvp.model.room.IRoomContract.*
import com.tamimattafi.mvp.model.room.async.*
import com.tamimattafi.mvp.model.room.async.ItemActionAsync.Companion.DELETE
import com.tamimattafi.mvp.model.room.async.ItemActionAsync.Companion.INSERT
import com.tamimattafi.mvp.model.room.async.ItemActionAsync.Companion.UPDATE
import com.tamimattafi.mvp.model.room.async.ListActionAsync.Companion.INSERT_ALL
import com.tamimattafi.mvp.model.room.async.ListActionAsync.Companion.UPDATE_ALL


open class RoomDataSource<T>(private val dao: IRoomDao<T>) : BaseDataSource(), IRoomDataSource<T> {


    override fun getList(query: SupportSQLiteQuery): ICallback<ArrayList<T>> = createCallback {
        ListAsync(it, dao).execute(query)
    }

    override fun getItem(query: SupportSQLiteQuery): ICallback<T> = createCallback {
        ItemAsync(it, dao).execute(query)
    }

    override fun delete(item: T): ICallback<Int> = createCallback {
        ItemActionAsync(it, dao).execute(AsyncAction(DELETE, item))
    }

    override fun update(item: T): ICallback<Int> = createCallback {
        ItemActionAsync(it, dao).execute(AsyncAction(UPDATE, item))
    }

    override fun insert(item: T): ICallback<Long> = createCallback {
        ItemActionAsync(it, dao).execute(AsyncAction(INSERT, item))
    }

    override fun insertAll(items: ArrayList<T>): ICallback<List<Long>> = createCallback {
        ListActionAsync(it, dao).execute(AsyncAction(INSERT_ALL, items))
    }

    override fun updateAll(items: ArrayList<T>): ICallback<Int> = createCallback {
        ListActionAsync(it, dao).execute(AsyncAction(UPDATE_ALL, items))
    }

    override fun deleteAll(items: ArrayList<T>): ICallback<Int> = createCallback {
        ListActionAsync(it, dao)
    }

}