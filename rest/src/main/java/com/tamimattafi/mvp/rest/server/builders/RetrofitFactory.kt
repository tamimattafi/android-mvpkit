package com.tamimattafi.mvp.repositories.api.server.builders

import com.tamimattafi.mvp.MvpBaseContract.Token
import com.tamimattafi.mvp.repositories.api.server.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun getBasicBuilder(baseLink: String): Retrofit.Builder
            = Retrofit.Builder()
                .baseUrl(baseLink)
                .addConverterFactory(GsonConverterFactory.create())

    fun <T> create(baseLink: String, clazz: Class<T>): T
            = getBasicBuilder(baseLink)
                .build()
                .create(clazz)

    fun <T> create(retrofit: Retrofit, clazz: Class<T>): T = retrofit.create(clazz)

    fun <T, R : Token> create(baseLink: String, clazz: Class<T>, authInterceptor: AuthInterceptor<R>): T
            = getBasicBuilder(baseLink)
                .client(OkHttpFactory.create(authInterceptor))
                .build()
                .create(clazz)

    fun <T> create(baseLink: String, clazz: Class<T>, okHttpClient: OkHttpClient)
            = getBasicBuilder(baseLink)
                .client(okHttpClient)
                .build()
                .create(clazz)
}