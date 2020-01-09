package com.tamimattafi.mvp.repositories.database.async

import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.repositories.database.BaseDao
import com.tamimattafi.mvp.threads.Async

class ItemActionAsync<T, R>(
    callback: MvpBaseContract.NotificationCallback<R>,
    private val dao: BaseDao<T>
) : Async<Pair<Int, T>, R>(callback) {

    override fun doWork(param: Pair<Int, T>): R = with(param) {
        dao.run {
            @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
            when (first) {
                INSERT -> insertItem(second)
                UPDATE -> updateItem(second)
                DELETE -> deleteItem(second)
                else -> throw IllegalArgumentException(UNKNOWN_TYPE + first.toString())
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