package com.tamimattafi.mvp.repositories.database.async

import com.tamimattafi.mvp.MvpBaseContract.NotificationCallback
import com.tamimattafi.mvp.repositories.database.BaseDao
import com.tamimattafi.mvp.core.threads.SimpleAsync

class ListActionAsync<T, R>(callback: NotificationCallback<R>, private val dao: BaseDao<T>) : com.tamimattafi.mvp.core.threads.SimpleAsync<Pair<Int, ArrayList<T>>, R>(callback) {

    override fun doWork(param: Pair<Int, ArrayList<T>>): R = with(param) {
        dao.run {
            @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
            when (first) {
                INSERT_ALL -> insertAll(second)
                else -> throw IllegalArgumentException(UNKNOWN_TYPE + first.toString())
            } as R
        }
    }

    companion object {
        const val INSERT_ALL = 0
        const val UNKNOWN_TYPE = "Unknown action type: "
    }

}