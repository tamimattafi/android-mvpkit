package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter

abstract class PagerRecyclerPresenter<T, H : Holder, V : ListenerView<H>, R : PagerListRepository<T>>(view: V, repository: R) :
    BaseRecyclerPresenter<T, H, V, R>(view, repository) {

    protected var page: Int = 1

    override fun loadRepositoryData() {

        with(view.getAdapter()) {
            repository.getDataList(page).addSuccessListener { data ->
                handleData(data)
            }.addFailureListener { message ->
                handleError(message)
            }.addCompleteListener {
                isLoading = false
            }.start()
        }

    }

    override fun handleError(message: String) {
        view.apply {
            (getAdapter() as PagerAdapter).hasPagingError = true
            showMessage(message)
        }
    }

    override fun handleData(data: ArrayList<T>) {
        this.data.apply {
            addAll(data)
            view.getAdapter().setTotalDataCount(size)
            page++
        }
    }

    override fun refresh() {
        page = 1
        super.refresh()
    }

}