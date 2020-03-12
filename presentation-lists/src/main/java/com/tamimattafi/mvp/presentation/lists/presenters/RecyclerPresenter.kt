package com.tamimattafi.mvp.presentation.lists.presenters

import com.tamimattafi.mvp.presentation.lists.presenters.global.BaseRecyclerPresenter
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.*
import com.tamimattafi.mvp.model.lists.IListDataSourceContract.*

abstract class RecyclerPresenter<T, H : IHolder, V : IListenerView<H>, R : IListDataSource<T>>(
    view: V,
    repository: R
) : BaseRecyclerPresenter<T, H, V, R>(view, repository), IRecyclerPresenter<H, V> {

    override fun loadDataFromSource() {

        viewController.tryCall {
            with(getAdapter()) {
                setTotalDataCount(0)
                dataSource.getDataList()
                    .addSuccessListener(this@RecyclerPresenter::handleData)
                    .addFailureListener(this@RecyclerPresenter::handleError)
                    .addCompleteListener {
                        isLoading = false
                    }.start()
            }
        }


    }

}