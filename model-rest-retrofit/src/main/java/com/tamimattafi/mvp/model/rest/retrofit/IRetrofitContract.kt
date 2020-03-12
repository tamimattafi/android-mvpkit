package com.tamimattafi.mvp.model.rest.retrofit

import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.model.IModelContract.IDataSource
import retrofit2.Call
import retrofit2.Response


interface IRetrofitContract {


    /**
     *
     * ##### TABLE OF CONTENT #####
     *
     *
     *
     * ## 1. Callbacks - Communication with the server
     * @see IRetrofitCallback
     *
     *
     *
     *
     *
     *
     */




    /**
     * A simplifier and more functional retrofit call-back
     *
     * @see com.tamimattafi.mvp.rest.retrofit.callbacks.RetrofitCallback for implementation info
     *
     * @see ICallback and
     * @see ICallbackNotifier for more information
     *
     */
    interface IRetrofitCallback<T, R> {



        /**
         * Enqueues the callback and assign this callback as a listener
         *
         * @param call: The call to be enqueued
         *
         * @see Call for more information
         *
         */
        fun start(call: Call<T>)



        /**
         * Adds a new success listener to the success sequence
         *
         * @param onSuccess: A lambda that will be invoked when the call is successful
         * this lambda contains a member constant of type Response<T> where <T> is the body
         *
         * @see Response for more information
         *
         */
        fun addSuccessListener(onSuccess: (response: Response<T>) -> Unit): IRetrofitCallback<T, R>



        /**
         * Adds a new failure listener to the failure sequence
         *
         * @param onFailure: A lambda that will be invoked when the call is successful
         * this lambda contains a member constant of type ICallbackError that describes the failure
         *
         * @see ICallbackError for more information
         *
         */
        fun addFailureListener(onFailure: (error: ICallbackError) -> Unit): IRetrofitCallback<T, R>



        /**
         * Adds a new complete listener to the complete sequence
         *
         * @param onComplete: A lambda that will be invoked when the call is successful
         *
         */
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


    /**
     * Each retrofit data source must extend this interface's implementation
     *
     * @see com.tamimattafi.mvp.rest.retrofit.data.RetrofitDataSource for implementation info
     *
     */
    interface IRetrofitDataSource : IDataSource


}
