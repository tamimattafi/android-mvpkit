package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter

abstract class PagerRecyclerPresenter<T, H : Holder, V : ListenerView<H>, R : PagerListDataSource<T>>(view: V, repository: R) :
    BaseRecyclerPresenter<T, H, V, R>(view, repository) {

    protected var page: Int = 1

    override fun loadRepositoryData() {

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

    override fun handleError(message: String) {
        view.tryCall {
            (getAdapter() as PagerAdapter).hasPagingError = true
            showMessage(message)
        }
    }

    override fun handleData(data: ArrayList<T>) {
        this.data.apply {
            addAll(data)
            view.tryCall {
                (getAdapter() as PagerAdapter).apply {
                    hasPagingError = false
                    setTotalDataCount(size)
                }
            }
            page++
        }
    }

    override fun refresh() {
        page = 1
        super.refresh()
    }

}