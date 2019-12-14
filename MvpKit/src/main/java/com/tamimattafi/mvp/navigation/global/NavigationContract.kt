package com.tamimattafi.mvp.navigation.global

import com.tamimattafi.mvp.navigation.fragment.NavigationFragment

interface NavigationContract {

    interface Manager {
        fun attachBaseFragment(fragment: NavigationFragment)
        fun attachFragment(fragment: NavigationFragment)
        fun requestBackPress()
    }

    interface Fragment {
        var name: String
        fun attachNavigationManager(navigationManager: Manager)
    }

    interface MvpFragment<P> {
        fun onSetupPresenter(): P
    }

    interface BackPressHandler {
        fun onBackPressed(): Boolean
    }

    interface SelectionListener {
        fun onSelected()
    }
}