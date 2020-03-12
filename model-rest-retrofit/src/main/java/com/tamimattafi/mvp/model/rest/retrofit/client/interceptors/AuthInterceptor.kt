package com.tamimattafi.mvp.model.rest.retrofit.client.interceptors

import com.tamimattafi.mvp.model.rest.IRestContract.*

open class AuthInterceptor<T : IToken>(private val authPreferences: IAuthPreferences<T>) : HeaderInterceptor(ValueGetter(authPreferences)) {

    override var header: String = HEADER_AUTH
    override var prefix: String = PREFIX_BARER
    override var separator: String = SEPARATOR_SPACE

    override fun getCondition(): Boolean = with(authPreferences) { isLoggedIn() && getToken() != null }


    class ValueGetter<T: IToken>(private val authPreferences: IAuthPreferences<T>) : () -> String {

        override fun invoke(): String
                = authPreferences.getToken()?.value.toString()

    }

    companion object {
        const val HEADER_AUTH: String = "Authorization"
        const val PREFIX_BARER: String = "Barer"
        const val SEPARATOR_SPACE = " "
    }



}