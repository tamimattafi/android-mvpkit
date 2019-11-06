package com.tamimattafi.mvp.api.interactor.client

import okhttp3.Interceptor
import okhttp3.Response
import com.tamimattafi.mvp.MvpBaseContract.*
import com.tamimattafi.mvp.api.interactor.InteractorConstants

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