package com.tamimattafi.mvp.repositories.api.server.interceptors

import com.tamimattafi.mvp.MvpBaseContract.AuthPreferences
import com.tamimattafi.mvp.MvpBaseContract.Token
import com.tamimattafi.mvp.repositories.api.server.ClientConstants

open class AuthInterceptor<T : Token>(private val authPreferences: AuthPreferences<T>) : HeaderInterceptor(valueGetter(authPreferences)) {

    override var header: String = ClientConstants.HEADER_AUTH
    override var prefix: String = ClientConstants.PREFIX_BARER
    override var separator: String = ClientConstants.SEPARATOR_SPACE
    override fun getCondition(): Boolean = with(authPreferences) { isLoggedIn() && getToken() != null }

    companion object {
        fun <T : Token> valueGetter(authPreferences: AuthPreferences<T>): () -> String = {
            authPreferences.getToken()?.value.toString()
        }
    }

}