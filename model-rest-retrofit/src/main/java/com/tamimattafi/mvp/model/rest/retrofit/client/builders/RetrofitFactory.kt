package com.tamimattafi.mvp.model.rest.retrofit.client.builders

import com.tamimattafi.mvp.model.rest.IRestContract.IToken
import com.tamimattafi.mvp.model.rest.retrofit.client.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {


    fun getBaseBuilder(baseLink: String): Retrofit.Builder
            = Retrofit.Builder()
                .baseUrl(baseLink)
                .addConverterFactory(GsonConverterFactory.create())


    fun <T> create(baseLink: String, clazz: Class<T>): T
            = getBaseBuilder(baseLink)
                .build()
                .create(clazz)


    fun <T> create(retrofit: Retrofit, clazz: Class<T>): T = retrofit.create(clazz)


    fun <T, R : IToken> create(baseLink: String, clazz: Class<T>, authInterceptor: AuthInterceptor<R>): T
            = getBaseBuilder(baseLink)
                .client(OkHttpFactory.create(authInterceptor))
                .build()
                .create(clazz)


    fun <T> create(baseLink: String, clazz: Class<T>, okHttpClient: OkHttpClient)
            = getBaseBuilder(baseLink)
                .client(okHttpClient)
                .build()
                .create(clazz)

}