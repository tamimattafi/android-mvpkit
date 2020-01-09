package com.tamimattafi.mvp.navigation.fragment

import android.os.Bundle
import android.view.View
import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.navigation.global.NavigationContract

abstract class MvpFragment<P : MvpBaseContract.Presenter> : NavigationFragment(),
    NavigationContract.MvpFragment<P> {

    protected var presenter: P? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePresenter()
    }

    private fun preparePresenter() {
        presenter?.onResume() ?: kotlin.run {
            presenter = onSetupPresenter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
        presenter = null
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

}