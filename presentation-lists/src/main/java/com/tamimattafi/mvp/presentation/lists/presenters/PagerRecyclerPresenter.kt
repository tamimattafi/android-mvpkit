package com.tamimattafi.mvp.presentation.lists.presenters

import com.tamimattafi.mvp.model.lists.IListDataSourceContract.IPagerListDataSource
import com.tamimattafi.mvp.presentation.lists.presenters.global.BaseRecyclerPresenter
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.*

abstract class PagerRecyclerPresenter<T, H : IHolder, V : IPagerListenerView<H>, R : IPagerListDataSource<T>>(view: V, repository: R) :
    BaseRecyclerPresenter<T, H, V, R>(view, repository) {

    protected var page: Int = 0
    protected var allData: Boolean = false

    override fun loadData() {
        if (!allData) super.loadData()
    }

    override fun loadDataFromSource() {
        viewController.tryCall {
            with(getAdapter()) {
                dataSource.getDataList(page)
                    .addSuccessListener(this@PagerRecyclerPresenter::handleData)
                    .addFailureListener(this@PagerRecyclerPresenter::handleError)
                    .addCompleteListener {
                        isLoading = false
                        notifyChanges()
                    }.start()
            }
        }
    }

    override fun handleData(data: ArrayList<T>) {
        this.allData = data.isEmpty()

        viewController.tryCall {
            getAdapter().apply {
                hasError = false
                if (!allData) {
                    this@PagerRecyclerPresenter.data.addAll(data)
                    addNewData(data.size)
                    page++
                }
            }
        }

    }

    override fun refresh() {
        viewController.tryCall {
            if (!getAdapter().isLoading) {
                page = 0
                allData = false
                super.refresh()
            }
        }
    }

}