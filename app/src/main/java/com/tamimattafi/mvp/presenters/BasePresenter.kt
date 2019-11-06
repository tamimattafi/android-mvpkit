package com.tamimattafi.mvp.presenters

import com.tamimattafi.mvp.MvpBaseContract.*

abstract class BasePresenter<V : View, R : Repository>(
    protected val view: V,
    protected val repository: R
) : Presenter {

    override fun onDestroyView() {
        repository.stopListening()
    }

}