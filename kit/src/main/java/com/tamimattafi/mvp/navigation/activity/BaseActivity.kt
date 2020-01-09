package com.tamimattafi.mvp.navigation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int

    abstract fun onViewCreated(savedInstanceState: Bundle?)
    abstract fun beforeViewCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeViewCreated(savedInstanceState)
        setContentView(layoutId)
        onViewCreated(savedInstanceState)
    }
}
