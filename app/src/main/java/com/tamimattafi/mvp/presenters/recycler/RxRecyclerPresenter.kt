package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RxRecyclerPresenter<T, H : Holder, V : ListenerView<H, Adapter>, R : RxListRepository<T>>(
    view: V,
    repository: R
) : BaseRecyclerPresenter<T, H, Adapter, V, R>(view, repository), MvpBaseContract.RecyclerPresenter<H> {

    private var disposable: Disposable? = null

    override fun loadRepositoryData() {
        with(view.getAdapter()) {
            disposable?.dispose()
            setTotalDataCount(0)
            disposable = repository.getDataList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { data ->
                    handleData(ArrayList(data))
                    isLoading = false
                }.doOnError {
                    handleError(with(it) { localizedMessage ?: message ?: toString() })
                }.subscribe()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }
}