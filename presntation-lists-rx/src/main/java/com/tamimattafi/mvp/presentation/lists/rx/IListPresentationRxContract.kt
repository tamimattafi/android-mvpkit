package com.tamimattafi.mvp.presentation.lists.rx

import com.tamimattafi.mvp.model.IModelContract.IDataSource
import io.reactivex.Flowable

interface IListPresentationRxContract {

    interface RxListDataSource<T> : IDataSource {
        fun getDataList(): Flowable<List<T>>
    }

}