package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter

abstract class PagerRecyclerPresenter<T, H : Holder, V : PagerListenerView<H>, R : PagerListDataSource<T>>(view: V, repository: R) :
    BaseRecyclerPresenter<T, H, V, R>(view, repository) {

    protected var page: Int = 0

    override fun loadRepositoryData() {

        view.tryCall {
            with(getAdapter()) {
                dataSource.getDataList(page).addSuccessListener { data ->
                    handleData(data)
                }.addFailureListener { message ->
                    handleError(message)
                }.addCompleteListener {
                    isLoading = false
                    notifyChanges()
                }.start()
            }
        }

    }

    override fun handleData(data: ArrayList<T>) {
        this.data.addAll(data)

        view.tryCall {
            getAdapter().apply {
                hasError = false
                setTotalDataCount(data.size)
            }
        }

        page++
    }

    override fun refresh() {
        page = 0
        super.refresh()
    }

}