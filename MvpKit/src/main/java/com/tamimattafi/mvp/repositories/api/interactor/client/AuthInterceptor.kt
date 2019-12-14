package com.tamimattafi.mvp.repositories.api.interactor.client

import com.tamimattafi.mvp.MvpBaseContract.AuthPreferences
import com.tamimattafi.mvp.MvpBaseContract.Token
import com.tamimattafi.mvp.repositories.api.interactor.InteractorConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor<T : Token>(private val authPreferences: AuthPreferences<T>) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        with(authPreferences) {
            val initialRequest = chain.request()
            return if (isLoggedIn() && getToken() != null) {
                val modifiedRequest = initialRequest.newBuilder()
                    .addHeader(
                        InteractorConstants.HEADER_AUTH,
                        InteractorConstants.HEADER_AUTH_BARER + " " + getToken()!!.value
                    )
                    .build()

                chain.proceed(modifiedRequest)
            } else chain.proceed(initialRequest)
        }
    }

}