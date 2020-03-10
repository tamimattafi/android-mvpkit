package com.tamimattafi.mvp.model.room.rx.database

import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.model.data.BaseDataSource
import com.tamimattafi.mvp.model.room.rx.IRoomRxContract.IRoomRxDao
import com.tamimattafi.mvp.model.room.rx.IRoomRxContract.IRoomRxDataSource
import io.reactivex.Completable
import io.reactivex.Flowable


open class RoomRxDataSource<T>(private val dao: IRoomRxDao<T>) : BaseDataSource(), IRoomRxDataSource<T> {

    override fun getList(query: SupportSQLiteQuery): Flowable<ArrayList<T>>
        = dao.getList(query)

    override fun getItem(query: SupportSQLiteQuery): Flowable<T>
        = dao.getItem(query)

    override fun insert(item: T): Completable
        = dao.insert(item)

    override fun insertAll(items: ArrayList<T>): Completable
        = dao.insertAll(items)

    override fun update(item: T): Completable
        = dao.update(item)

    override fun updateAll(items: ArrayList<T>): Completable
        = dao.updateAll(items)

    override fun delete(item: T): Completable
        = dao.delete(item)

    override fun deleteAll(items: ArrayList<T>): Completable
        = dao.deleteAll(items)


}