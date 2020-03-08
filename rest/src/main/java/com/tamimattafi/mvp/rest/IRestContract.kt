package com.tamimattafi.mvp.rest

import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName
import com.tamimattafi.mvp.core.ICoreContract.*
import retrofit2.Call
import retrofit2.Response


interface IRestContract {


    /**
     *
     * ##### TABLE OF CONTENT #####
     *
     *
     *
     * ## 1. Preferences - Light data storage
     * @see IPreferencesProvider
     * @see IAuthPreferences
     *
     *
     * ## 2. Authentication - Server usage authorization
     * @see IToken
     *
     *
     *
     *
     */





    /**
     * This interface is used to make this layer consume SharedPreferences but at the same time be Context independent
     * This should be implemented by Application class or some Activity that is able to provide Context
     *
     * @see SharedPreferences for more information
     *
     */
    interface IPreferencesProvider {
        fun getPreferences(name: String): SharedPreferences
    }




    /**
     * This interface is used to manage user state and authorization
     *
     * @see com.tamimattafi.mvp.rest.preferences.AuthPreferences for implementation info
     *
     */
    interface IAuthPreferences<T : IToken> {


        /**
         * Stores user's current authentication status
         *
         * @param isLogged: if set to true means the user is authorized and no longer has to login
         * else means the user has to re-authenticate
         *
         */
        fun setLoggedIn(isLogged: Boolean)



        /**
         * Determines whether the user is already logged in or not to skip authentication step
         *
         * if value is true means the user is authorized and there's no need to login again
         * else means the user has to re-authenticate
         *
         */
        fun isLoggedIn(): Boolean



        /**
         * Stores the token that will be used for authorization
         *
         * @param token: contains information required to authorize the user to connect with the server
         *
         */
        fun setToken(token: T?)



        /**
         * Retrieves the token stored and will be used for authorization
         *
         * @see setToken for more information
         *
         */
        fun getToken(): T?

    }




    /**
     * Every class that is used to parse login response that contains an authorization token must implement this interface
     * Pay attention to Serialization names, use @SerializedName for value to match token property name in your JSON response
     *
     * @see SerializedName for more information
     *
     */
    interface IToken {
        val value: String
    }




    /**
     * A simplifier and more functional retrofit call-back
     *
     * @see com.tamimattafi.mvp.rest.callbacks.RetrofitCallback for implementation info
     * @see ICallback and
     * @see ICallbackNotifier for more information
     *
     */
    interface IRetrofitCallback<T, R> {
        fun start(call: Call<T>)
        fun addSuccessListener(onSuccess: (response: Response<T>) -> Unit): IRetrofitCallback<T, R>
        fun addFailureListener(onFailure: (error: ICallbackError) -> Unit): IRetrofitCallback<T, R>
        fun addCompleteListener(onComplete: () -> Unit): IRetrofitCallback<T, R>




        /**
         * When this method is called it prevents the call-back from notifying other listeners
         * The call itself is also canceled here
         *
         * @see ICallbackNotifier and
         * @see retrofit2.Call for more information
         *
         */
        fun cancel()
    }



}
