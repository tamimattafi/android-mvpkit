package com.tamimattafi.mvp.core

import android.util.Log

interface ICoreContract {


    /**
     *
     * ##### TABLE OF CONTENT #####
     *
     *
     *
     * ## 1. Callbacks - Communication between layers ##
     * @see ICallback
     * @see ICallbackNotifier
     * @see ICallbackManager
     * @see ICallbackError
     *
     *
     *
     *
     *
     */


    /**
     * This is the interface that should be returned to the consumer
     * It limits the consumer from notifying any listeners
     * It makes the consumer able to only attach listeners, clear them, or cancel and start the process
     *
     * @see ICallbackNotifier for more information
     * @see com.tamimattafi.mvp.core.callbacks.Callback for implementation info
     *
     */
    interface ICallback<T> {

        /**
         * Adds a new success listener to the success sequence
         *
         * @param onSuccess: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing is successful
         *
         * Inside this lambda you will have a member constant of type 'T' which is the result returned
         * by the processor
         *
         * @see ICallbackNotifier.notifySuccess for more information
         *
         */
        fun addSuccessListener(onSuccess: (data: T) -> Unit): ICallback<T>


        /**
         * Adds a new failure listener to the failure sequence
         *
         * @param onFailure: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing fails
         *
         * Inside this lambda you will have a member constant of type ICallbackError which is the error returned
         * by the processor
         *
         * @see ICallbackNotifier.notifyFailure and
         * @see ICallbackError for more information
         *
         */
        fun addFailureListener(onFailure: (error: ICallbackError) -> Unit): ICallback<T>


        /**
         * Adds a new complete listener to the complete sequence
         *
         * @param onComplete: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing is completed
         *
         * Note that this lambda will be also invoked after all success or failure listeners are invoked
         *
         * @see ICallbackNotifier.notifyComplete for more information
         *
         */
        fun addCompleteListener(onComplete: () -> Unit): ICallback<T>


        /**
         * Adds a new progress listener to the progress sequence
         *
         * @param onProgress: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing fails
         *
         * Inside this lambda you will have a member constant of type Int which is the current progress of execution returned
         * by the processor
         *
         * @see ICallbackNotifier.notifyProgress for more information
         *
         */
        fun addProgressListener(onProgress: (progress: Int) -> Unit): ICallback<T>


        /**
         * Adds a new cancel listener to the cancel sequence
         *
         * @param onCancel: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing is canceled
         *
         * @see ICallbackNotifier.notifyCancel for more information
         *
         */
        fun addCancelListener(onCancel: () -> Unit): ICallback<T>


        /**
         * Adds a new start listener to the start sequence
         *
         * @param onStart: a lambda with a return of type Unit. This lambda will be invoked
         * when the processing is started
         *
         * @see ICallbackNotifier.notifyStart for more information
         *
         */
        fun addStartListener(onStart: () -> Unit): ICallback<T>


        /**
         * Triggers the processing logic, aka the lambda assigned by setAction method
         *
         * @see ICallbackManager.setAction for more information
         *
         * @see addStartListener: All start listeners will be triggered by this function
         *
         */
        fun start()


        /**
         * Clears all listening sequences such as success and failure listeners
         *
         */
        fun clearListeners()


        /**
         * Clears all listening sequences, clears the action set by setAction and stops processing logic if possible
         *
         * @see clearListeners and
         * @see ICallbackManager.setAction for more information
         *
         * @see addCancelListener: All cancel listeners will be triggered by this function
         *
         */
        fun cancel()
    }





    /**
     * This is the interface that should be returned to the data processor
     * It limits the processor from starting, canceling or attaching listeners
     * It makes the processor able to trigger actions only
     *
     * @see ICallback for more information
     * @see com.tamimattafi.mvp.core.callbacks.CallbackNotifier for implementation info
     *
     */
    interface ICallbackNotifier<T> {


        /**
         * Triggers all the successListener attached to ICallback
         *
         * @param data: the data that will be sent to these listeners
         *
         * @see ICallback.addSuccessListener for more information
         *
         */
        fun notifySuccess(data: T)


        /**
         * Triggers all the failure listeners attached to ICallback
         *
         * @param error: the error that will be sent to these listeners
         *
         * @see ICallback.addFailureListener and
         * @see ICallbackError for more information
         *
         */
        fun notifyFailure(error: ICallbackError)


        /**
         * Triggers all the complete listeners attached to ICallback
         * This will be automatically triggered after success or failure triggers
         *
         * @see ICallback.addCompleteListener for more information
         *
         */
        fun notifyComplete()


        /**
         * Triggers all the progress listeners attached to ICallback
         *
         * @param progress: the current execution progress value returned by the processor
         *
         * @see ICallback.addProgressListener for more information
         *
         */
        fun notifyProgress(progress: Int)

        /**
         * Triggers all the start listeners attached to ICallback
         *
         * @see ICallback.addStartListener for more information
         *
         */
        fun notifyStart()



        /**
         * Triggers all the cancel listeners attached to ICallback
         *
         * @see ICallback.addCancelListener for more information
         *
         */
        fun notifyCancel()



    }





    /**
     * This interface should be implemented by a class that will hold both call-back and it's notifier
     * and makes a connection between them
     *
     * @see ICallback and
     * @see ICallbackNotifier for more information
     *
     * @see com.tamimattafi.mvp.core.callbacks.CallbackManager for implementation info
     *
     */
    interface ICallbackManager<T> {

        /**
         * Returns the call-back that will be visible to the caller
         *
         * @see ICallback for more information
         *
         */
        fun getCallback(): ICallback<T>


        /**
         * Returns the notifier that will be visible to the processor
         *
         * @see ICallbackNotifier for more information
         *
         */
        fun getCallbackNotifier(): ICallbackNotifier<T>


        /**
         * Sets the action of this call that will be triggered by start function
         * @see ICallback.start for more information
         *
         * @param action: lambda with Unit return type that will be invoked by start method
         * Inside this lambda you will have a member constant with the name 'it' of type ICallbackNotifier
         *
         * @see ICallbackNotifier for more information
         *
         */
        fun setAction(action: (notifier: ICallbackNotifier<T>) -> Unit): ICallbackManager<T>
    }


    /**
     * Every error returned by a call-back must implement this interface
     *
     * @see ICallbackNotifier.notifyFailure for more info
     *
     */
    interface ICallbackError {


        /**
         * Short definition of the occurred error and sometimes might contain a suggestion or a solution
         *
         * @see Exception for more information
         *
         */
        var message: String?


        /**
         * Short definition of the occurred error but less technical and more user-friendly, also might contain a suggestion or a solution
         * and also might match user's local and language
         *
         * @see Exception for more information
         *
         */
        var localizedMessage: String?


        /**
         * A code that describes certain error such as REST response codes
         *
         * @see Exception for more information
         *
         */
        var code: Int?
    }








}