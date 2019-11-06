package com.tamimattafi.mvp.api.interactor.client

import com.tamimattafi.mvp.MvpBaseContract.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object AuthHttpClient {

    const val TIMEOUT_READ = 40L
    const val TIMEOUT_WRITE = 40L
    val UNIT = TimeUnit.SECONDS

    private var instance: OkHttpClient? = null

    fun <T: Token> getInstance(authInterceptor: AuthInterceptor<T>): OkHttpClient =
        instance ?: createInstance(authInterceptor).also { instance = it }

    private fun <T: Token> createInstance(authInterceptor: AuthInterceptor<T>): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(TIMEOUT_READ, UNIT)
            .writeTimeout(TIMEOUT_WRITE, UNIT)
            .build()

}