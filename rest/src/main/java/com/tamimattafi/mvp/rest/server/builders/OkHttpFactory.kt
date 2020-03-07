package com.tamimattafi.mvp.repositories.api.server.builders

import android.util.Log
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    const val TIMEOUT_READ = 40L
    const val TIMEOUT_WRITE = 40L
    val UNIT = TimeUnit.SECONDS

    fun getBasicBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(TIMEOUT_READ, UNIT)
        .writeTimeout(TIMEOUT_WRITE, UNIT)
        .addInterceptor(HttpLoggingInterceptor { Log.d(OkHttpClient::class.java.simpleName, it) }.apply { level = HttpLoggingInterceptor.Level.BODY })

    fun create(vararg interceptors: Interceptor): OkHttpClient
            = getBasicBuilder().apply {
                interceptors.forEach {
                    addInterceptor(it)
                }
            }.build()

    fun create(cache: Cache, vararg interceptors: Interceptor): OkHttpClient
            = create(*interceptors).newBuilder().cache(cache).build()

}