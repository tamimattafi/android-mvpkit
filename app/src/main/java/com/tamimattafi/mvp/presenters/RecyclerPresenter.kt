package com.tamimattafi.mvp.presenters

import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.MvpBaseContract.*

abstract class RecyclerPresenter<T, H : Holder, V : ListenerView<H>, R : ListRepository<T>>(
    view: V,
    repository: R
) : BasePresenter<V, R>(view, repository), MvpBaseContract.RecyclerPresenter<H> {

    protected val data: ArrayList<T> = ArrayList()

    override fun loadData() {
        view.apply {

            if (!getAdapter().isLoading) {
                loadRepositoryData()
            } else showMessage(PresenterConstants.LOAD_DATA_ERROR)

        }
    }

    private fun loadRepositoryData() {

        view.getAdapter().apply {
            isLoading = true
            setDataCount(0)
        }

        repository.getData().addSuccessListener { data ->
            handleData(data)
        }.addFailureListener { message ->
            handleError(message)
        }.addCompleteListener {
            view.getAdapter().isLoading = false
        }.start()

    }

    open fun handleError(message: String) {
        this.data.clear()

        view.apply {
            getAdapter().setDataCount(0)
            showMessage(message)
        }
    }

    open fun handleData(data: ArrayList<T>) {
        this.data.apply {
            clear()
            addAll(data)
        }

        view.getAdapter().setDataCount(data.size)
    }

    override fun refresh() {
        view.apply {
            if (!getAdapter().isLoading) {
                data.clear()
                loadRepositoryData()
            } else showMessage(PresenterConstants.REFRESH_ERROR)
        }
    }

}