package com.tamimattafi.mvp.model.lists

import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.model.IModelContract.*

interface IListDataSourceContract {

    interface IListDataSource<T> : IDataSource {
        fun getDataList(): ICallback<ArrayList<T>>
    }

    interface IPagerListDataSource<T> : IDataSource {
        fun getDataList(page: Int): ICallback<ArrayList<T>>
    }
}