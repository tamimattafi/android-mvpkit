package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter

abstract class PagerRecyclerPresenter<T, H : Holder, V : PagerListenerView<H>, R : PagerListDataSource<T>>(view: V, repository: R) :
    BaseRecyclerPresenter<T, H, V, R>(view, repository) {

    protected var page: Int = 0
    protected var allData: Boolean = false

    override fun loadRepositoryData() {
        if (!allData) {

            view.tryCall {
                with(getAdapter()) {
                    dataSource.getDataList(page).addSuccessListener { data ->
                        handleData(data)
                    }.addFailureListener { message ->
                        handleError(message)
                    }.addCompleteListener {
                        isLoading = false
                    }.start()
                }
            }

        }
    }

    override fun handleData(data: ArrayList<T>) {
        this.allData = data.isEmpty()

        view.tryCall {
            getAdapter().apply {
                hasError = false
                if (!allData) {
                    this@PagerRecyclerPresenter.data.addAll(data)
                    addNewData(data.size)
                    page++
                } else notifyChanges()
            }
        }

    }

    override fun refresh() {
        page = 0
        super.refresh()
    }

}