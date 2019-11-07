package com.tamimattafi.mvp.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable

interface BaseDao<T> {

    @RawQuery
    fun getRxList(query: SupportSQLiteQuery): Flowable<List<T>>

    @RawQuery
    fun getRxItem(query: SupportSQLiteQuery): Flowable<T>

    @RawQuery
    fun getList(query: SupportSQLiteQuery): List<T>

    @RawQuery
    fun getItem(query: SupportSQLiteQuery): T

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(items: ArrayList<T>): List<Long>

    @Delete
    fun deleteItem(item: T): Int

    @Update
    fun updateItem(item: T): Int

}