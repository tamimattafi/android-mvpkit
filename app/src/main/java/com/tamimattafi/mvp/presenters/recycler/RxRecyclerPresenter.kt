package com.tamimattafi.mvp.presenters.recycler

import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.presenters.recycler.global.BaseRecyclerPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RxRecyclerPresenter<T, H : Holder, V : ListenerView<H>, R : RxListRepository<T>>(
    view: V,
    repository: R
) : BaseRecyclerPresenter<T, H, V, R>(view, repository), MvpBaseContract.RecyclerPresenter<H> {

    private var disposable: Disposable? = null

    override fun loadRepositoryData() {
        with(view.getAdapter()) {
            disposable?.dispose()
            setDataCount(0)
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