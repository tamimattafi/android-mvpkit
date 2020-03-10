package com.tamimattafi.mvp.model.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.tamimattafi.mvp.core.ICoreContract.ICallback
import com.tamimattafi.mvp.model.IModelContract.IDataSource


interface IRoomContract {


    interface IRoomDataSource<T> : IDataSource {
        fun getList(query: SupportSQLiteQuery): ICallback<ArrayList<T>>
        fun getItem(query: SupportSQLiteQuery): ICallback<T>

        fun insert(item: T): ICallback<Long>
        fun insertAll(items: ArrayList<T>): ICallback<List<Long>>

        fun update(item: T): ICallback<Int>
        fun updateAll(items: ArrayList<T>): ICallback<Int>

        fun delete(item: T): ICallback<Int>
        fun deleteAll(items: ArrayList<T>): ICallback<Int>
    }


    interface IRoomDao<T> {

        @RawQuery
        fun getList(query: SupportSQLiteQuery): List<T>

        @RawQuery
        fun getItem(query: SupportSQLiteQuery): T

        @Insert
        fun insert(item: T): Long

        @Insert
        fun insertAll(items: ArrayList<T>): List<Long>

        @Update
        fun update(item: T): Int

        @Update
        fun updateAll(items: ArrayList<T>): Int

        @Delete
        fun delete(item: T): Int

        @Delete
        fun deleteAll(items: ArrayList<T>): Int

    }

}