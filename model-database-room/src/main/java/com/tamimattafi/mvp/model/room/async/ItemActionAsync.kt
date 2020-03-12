@file:Suppress("UNCHECKED_CAST")

package com.tamimattafi.mvp.model.room.async

import com.tamimattafi.mvp.core.ICoreContract.ICallbackNotifier
import com.tamimattafi.mvp.core.threads.SimpleAsync
import com.tamimattafi.mvp.model.room.IRoomContract.IRoomDao

class ItemActionAsync<T, R>(callback: ICallbackNotifier<R>, private val dao: IRoomDao<T>) : SimpleAsync<AsyncAction<T>, R>(callback) {

    override fun doWork(param: AsyncAction<T>): R = with(param) {
        dao.run {
            when (action) {
                INSERT -> insert(data)
                UPDATE -> update(data)
                DELETE -> delete(data)
                else -> throw IllegalArgumentException(UNKNOWN_TYPE + action.toString())
            } as R
        }
    }

    companion object {
        const val INSERT = 0
        const val UPDATE = 1
        const val DELETE = 2

        const val UNKNOWN_TYPE = "Unknown action type: "
    }

}