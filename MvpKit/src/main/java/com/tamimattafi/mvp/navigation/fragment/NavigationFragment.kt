package com.tamimattafi.mvp.navigation.fragment

import com.tamimattafi.mvp.navigation.global.NavigationContract.Fragment
import com.tamimattafi.mvp.navigation.global.NavigationContract.Navigator

abstract class NavigationFragment : BaseFragment(), Fragment {

    protected lateinit var navigator: Navigator

    override fun attachNavigationManager(navigator: Navigator) {
        this.navigator = navigator
    }

}