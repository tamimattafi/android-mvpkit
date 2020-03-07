package com.tamimattafi.mvp.rest.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tamimattafi.mvp.rest.IRestContract.*

/**
 * Implementation of IAuthPreferences
 *
 * @see IAuthPreferences for more information
 *
 * @param preferencesProvider: This is used to make this class Context independent
 * @see IPreferencesProvider for more information
 *
 */
open class AuthPreferences<T : IToken>(preferencesProvider: IPreferencesProvider) : IAuthPreferences<T> {



    /**
     * Storage where all data will be saved
     *
     * @see SharedPreferences for more information
     *
     */
    protected open val sharedPreferences: SharedPreferences = preferencesProvider.getPreferences(PREFERENCES_NAME)



    /**
     * @see IAuthPreferences.setLoggedIn for more information
     *
     */
    override fun setLoggedIn(isLogged: Boolean) = with(sharedPreferences.edit()) {
        putBoolean(LOGIN_KEY, isLogged)
        apply()
    }



    /**
     * @see IAuthPreferences.isLoggedIn for more information
     *
     */
    override fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(LOGIN_KEY, false)



    /**
     * @see IAuthPreferences.setToken for more information
     *
     */
    override fun setToken(token: T?) = with(sharedPreferences.edit()) {
        putString(TOKEN_KEY, Gson().toJson(token))
        apply()
    }



    /**
     * @see IAuthPreferences.getToken for more information
     *
     */
    override fun getToken(): T? =
        Gson().fromJson(sharedPreferences.getString(TOKEN_KEY, null), object : TypeToken<T>() {}.type)


    /**
     * Values used for storage
     *
     */
    companion object {
        const val LOGIN_KEY = "login"
        const val TOKEN_KEY = "token"
        const val PREFERENCES_NAME = "auth-preferences"
    }

}