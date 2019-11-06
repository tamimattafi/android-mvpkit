package com.tamimattafi.mvp.api.auth

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tamimattafi.mvp.MvpBaseContract
import com.tamimattafi.mvp.MvpBaseContract.Token

class AuthPreferences<T : Token>(private val sharedPreferences: SharedPreferences) : MvpBaseContract.AuthPreferences<T> {

    override fun setLoggedIn(boolean: Boolean) = with(sharedPreferences.edit()) {
        putBoolean(LOGIN_KEY, boolean)
        apply()
    }

    override fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(LOGIN_KEY, false)


    override fun setToken(token: T?) = with(sharedPreferences.edit()) {
        putString(TOKEN_KEY, Gson().toJson(token))
        apply()
    }

    override fun getToken(): T? =
        Gson().fromJson(sharedPreferences.getString(TOKEN_KEY, null), object : TypeToken<T>() {}.type)

    companion object {
        const val LOGIN_KEY = "login-key"
        const val TOKEN_KEY = "token-key"
    }

}