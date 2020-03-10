@file:Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")

package com.tamimattafi.mvp.model.room.async

import com.tamimattafi.mvp.core.ICoreContract.ICallbackNotifier
import com.tamimattafi.mvp.core.threads.SimpleAsync
import com.tamimattafi.mvp.model.room.IRoomContract.IRoomDao

class ListActionAsync<T, R>(callback: ICallbackNotifier<R>, private val dao: IRoomDao<T>) : SimpleAsync<AsyncAction<ArrayList<T>>, R>(callback) {

    override fun doWork(param: AsyncAction<ArrayList<T>>): R = with(param) {
        dao.run {
            when (action) {
                INSERT_ALL -> insertAll(data)
                UPDATE_ALL -> updateAll(data)
                DELETE_ALL -> deleteAll(data)
                else -> throw IllegalArgumentException(UNKNOWN_TYPE + action.toString())
            } as R
        }
    }

    companion object {
        const val INSERT_ALL = 0
        const val UPDATE_ALL = 1
        const val DELETE_ALL = 2
        const val UNKNOWN_TYPE = "Unknown action type: "
    }

}