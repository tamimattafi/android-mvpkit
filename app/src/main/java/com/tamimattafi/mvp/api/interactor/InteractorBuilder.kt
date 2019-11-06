package com.tamimattafi.mvp.api.interactor

import com.tamimattafi.mvp.MvpBaseContract.Token
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tamimattafi.mvp.api.interactor.client.AuthHttpClient
import com.tamimattafi.mvp.api.interactor.client.AuthInterceptor

object InteractorBuilder {

    fun getBaseBuilder(baseLink: String): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(baseLink)
        .addConverterFactory(GsonConverterFactory.create())

    fun <T> createInteractor(baseLink: String, clazz: Class<T>): T = getBaseBuilder(baseLink).build().create(clazz)

    fun <T, R : Token> createInteractor(baseLink: String, clazz: Class<T>, authInterceptor: AuthInterceptor<R>): T =
        getBaseBuilder(baseLink)
            .client(AuthHttpClient.getInstance(authInterceptor))
            .build()
            .create(clazz)
}