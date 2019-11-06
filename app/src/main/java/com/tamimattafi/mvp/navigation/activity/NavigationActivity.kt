package com.tamimattafi.mvp.navigation.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tamimattafi.mvp.navigation.fragment.NavigationFragment
import com.tamimattafi.mvp.navigation.global.NavigationContract.*

abstract class NavigationActivity : BaseActivity(), Manager {

    abstract val rootId: Int

    private val currentFragment: NavigationFragment?
        get() = supportFragmentManager.findFragmentById(rootId) as? NavigationFragment

    private var baseFragment: NavigationFragment? = null

    override fun beforeViewCreated(savedInstanceState: Bundle?) {
        supportFragmentManager.addOnBackStackChangedListener {
            (currentFragment as? SelectionListener)?.onSelected()
        }
    }

    override fun attachBaseFragment(fragment: NavigationFragment) {
        fragment.attachNavigationManager(this)
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.transaction {
            replace(rootId, fragment)
        }
        baseFragment = fragment
    }

    override fun attachFragment(fragment: NavigationFragment) {
        fragment.attachNavigationManager(this)
        supportFragmentManager.transaction {
            add(rootId, fragment).addToBackStack(fragment.name)
        }
    }

    override fun requestBackPress() {
        onBackPressed()
    }

    private inline fun FragmentManager.transaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().transaction().commit()
    }

    override fun onBackPressed() {
        (currentFragment as? BackPressHandler)?.let {
            if (it.onBackPressed()) super.onBackPressed()
        } ?: super.onBackPressed()
    }
}