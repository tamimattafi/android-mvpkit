package com.tamimattafi.mvp.core

interface CoreContract {


    /**
     * This is the interface that should be returned to the caller
     * It limits the caller from notifying any listeners
     * It makes the caller able to only attach listeners, or cancel and start the process
     *
     * @see addSuccessListener: adds a new success listener to the success sequence
     * @see addFailureListener: adds a new failure listener to the failure sequence
     * @see addCompleteListener: adds a new complete listener to the complete sequence
     * @see start: will trigger the processing logic
     * @see cancel: will clear all listening sequences and cancels processing logic if possible
     *
     * @see ICallbackNotifier for more information
     */
    interface ICallback<T> {
        fun addSuccessListener(onSuccess: (data: T) -> Unit): ICallback<T>
        fun addFailureListener(onFailure: (message: String) -> Unit): ICallback<T>
        fun addCompleteListener(onComplete: () -> Unit): ICallback<T>
        fun start()
        fun cancel()
    }


    /**
     * This is the interface that should be returned to the data processor
     * It limits the processor from starting, canceling or attaching listeners
     * It makes the processor able to trigger actions only
     *
     * @see notifySuccess: triggers all the successListener attached to ICallback
     * @see notifyFailure: triggers all the failure listeners attached to ICallback
     * @see notifyComplete: triggers all the complete listeners attached to ICallback
     *
     * @see ICallback for more information
     */
    interface ICallbackNotifier<T> {
        fun notifySuccess(data: T)
        fun notifyFailure(message: String)
        fun notifyComplete()
    }

    interface ICallbackManager<T> {
        fun getCallback(): ICallbackManager<T>
        fun getCallbackNotifier(): ICallbackNotifier<T>
        fun setAction(action: (notifier: ICallbackNotifier<T>) -> Unit): ICallbackManager<T>
    }

    /**
     * This interface must be implemented by every data-source that stores states such as repositories and interactors
     * @see release: this method must clear all saved states and cancel all current processes if possible
     * It also must be called when data listener is destroyed such as presenters or when other data-sources are released
     *
     * @see IPresenter for more information
     * @see IViewController for more information
     *
     */
    interface IDataSource {
        fun release()
    }

    /**
     * This interface must be implemented by every presenter in the app
     * It will be the communication contract with the view controller in order to avoid concrete dependency and coupling
     * Its lifecycle must be bound with its view's lifecycle in order to avoid leaking and memory problems
     * @see IViewController for more information
     *
     * @see onDestroy: when this method is called, all data-sources must be released
     * @see IDataSource for more information
     */
    interface IPresenter<V: IViewController> {
        fun onPause()
        fun onResume()
        fun onDestroyView()
        fun onDestroy()
    }

    /**
     * This interface should be implemented by every view controller such as fragments or activity
     * It will be the communication contract with the presenter in order to avoid concrete dependency and coupling
     * @see IPresenter for more information
     *
     * @see IPresenter.onPause: Presenter's method that must be called when the view controller is paused
     * @see IPresenter.onResume: Presenter's method that must be called when the view controller is resumed
     * @see IPresenter.onDestroyView: Presenter's method that must be called when the view is destroyed
     * @see IPresenter.onDestroy: Presenter's method that must be called when the view controller is destroyed
     */
    interface IViewController {
        fun showMessage(message: String)
    }



}