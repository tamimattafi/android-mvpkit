package com.tamimattafi.mvp.presentation.lists.presenters.global

import com.tamimattafi.mvp.core.ICoreContract.ICallbackError
import com.tamimattafi.mvp.model.IModelContract.IDataSource
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.*
import com.tamimattafi.mvp.presentation.presenters.BasePresenter

abstract class BaseRecyclerPresenter<T, H : IHolder, V : IListenerView<H>, R : IDataSource>(
    view: V,
    repository: R
) : BasePresenter<V, R>(view, repository), IRecyclerPresenter<H, V> {

    protected val data: ArrayList<T> = ArrayList()
    abstract fun loadDataFromSource()

    override fun loadData() {
        viewController.tryCall {
            getAdapter().apply {
                if (!isLoading) {
                    hasError = false
                    isLoading = true
                    notifyChanges()
                    loadDataFromSource()
                }
            }
        }
    }

    open fun handleError(error: ICallbackError) {
        this.data.clear()

        viewController.tryCall {
            getAdapter().apply {
                hasError = true
                setTotalDataCount(0)
            }
            showMessage(error.message.toString())
        }
    }

    open fun handleData(data: ArrayList<T>) {
        this.data.apply {
            clear()
            addAll(data)
        }

        viewController.tryCall {
            getAdapter().apply {
                hasError = false
                setTotalDataCount(data.size)
            }
        }
    }

    override fun refresh() {
        viewController.tryCall {
            getAdapter().apply {
                if (!isLoading) {
                    setTotalDataCount(0)
                    dataSource.release()
                    data.clear()
                    loadData()
                }
            }
        }
    }

}