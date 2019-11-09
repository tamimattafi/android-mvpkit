package com.tamimattafi.mvp.presenters.recycler.global

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.BasePresenter

abstract class BaseRecyclerPresenter<T, H : Holder, V : ListenerView<H>, R : Repository>(
    view: V,
    repository: R
) : BasePresenter<V, R>(view, repository), RecyclerPresenter<H> {

    protected val data: ArrayList<T> = ArrayList()
    abstract fun loadRepositoryData()

    override fun loadData() {
        view.apply {
            if (!getAdapter().isLoading) {
                getAdapter().isLoading = true
                loadRepositoryData()
            }
        }
    }

    open fun handleError(message: String) {
        this.data.clear()

        view.apply {
            getAdapter().setTotalDataCount(0)
            showMessage(message)
        }
    }

    open fun handleData(data: ArrayList<T>) {
        this.data.apply {
            clear()
            addAll(data)
        }

        view.getAdapter().setTotalDataCount(data.size)
    }

    override fun refresh() {
        view.getAdapter().apply {
            if (!isLoading) {
                isLoading = true
                setTotalDataCount(0)
                data.clear()
                loadRepositoryData()
            }
        }
    }

}