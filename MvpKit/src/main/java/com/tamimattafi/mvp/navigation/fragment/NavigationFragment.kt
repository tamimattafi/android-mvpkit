package com.tamimattafi.mvp.navigation.fragment

import com.tamimattafi.mvp.navigation.global.NavigationContract.Fragment
import com.tamimattafi.mvp.navigation.global.NavigationContract.Manager

abstract class NavigationFragment : BaseFragment(), Fragment {

    protected lateinit var navigationManager: Manager

    override fun attachNavigationManager(navigationManager: Manager) {
        this.navigationManager = navigationManager
    }

}