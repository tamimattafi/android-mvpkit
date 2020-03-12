package com.tamimattafi.mvp.model.room.rx

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.ICoreContract.ICallback
import com.tamimattafi.mvp.model.IModelContract.IDataSource
import io.reactivex.Completable
import io.reactivex.Flowable


interface IRoomRxContract {


    interface IRoomRxDataSource<T> : IDataSource {
        fun getList(query: SupportSQLiteQuery): Flowable<ArrayList<T>>
        fun getItem(query: SupportSQLiteQuery): Flowable<T>

        fun insert(item: T): Completable
        fun insertAll(items: ArrayList<T>): Completable

        fun update(item: T): Completable
        fun updateAll(items: ArrayList<T>): Completable

        fun delete(item: T): Completable
        fun deleteAll(items: ArrayList<T>): Completable
    }


    interface IRoomRxDao<T> {

        @RawQuery
        fun getList(query: SupportSQLiteQuery): Flowable<ArrayList<T>>

        @RawQuery
        fun getItem(query: SupportSQLiteQuery): Flowable<T>

        @Insert
        fun insert(item: T): Completable

        @Insert
        fun insertAll(items: ArrayList<T>): Completable

        @Update
        fun update(item: T): Completable

        @Update
        fun updateAll(items: ArrayList<T>): Completable

        @Delete
        fun delete(item: T): Completable

        @Delete
        fun deleteAll(items: ArrayList<T>): Completable

    }

}