package com.tamimattafi.mvp.presentation.lists.rx.presenters

import com.tamimattafi.mvp.core.callbacks.CallbackError
import com.tamimattafi.mvp.presentation.lists.IListPresentationContract.*
import com.tamimattafi.mvp.presentation.lists.presenters.global.BaseRecyclerPresenter
import com.tamimattafi.mvp.presentation.lists.rx.IListPresentationRxContract.RxListDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RxRecyclerPresenter<T, H : IHolder, V : IListenerView<H>, R : RxListDataSource<T>>(
    view: V,
    repository: R
) : BaseRecyclerPresenter<T, H, V, R>(view, repository), IRecyclerPresenter<H, V> {

    private var disposable: Disposable? = null

    override fun loadDataFromSource() {
        viewController.tryCall {
            with(getAdapter()) {
                disposable?.dispose()
                setTotalDataCount(0)
                disposable = dataSource.getDataList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { data ->
                        handleData(ArrayList(data))
                        isLoading = false
                    }.doOnError {
                        handleError(CallbackError(it.message, it.localizedMessage))
                    }.subscribe()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }
}