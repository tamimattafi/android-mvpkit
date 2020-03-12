package com.tamimattafi.mvp.presentation

import android.util.Log
import com.tamimattafi.mvp.core.ICoreContract.*
import com.tamimattafi.mvp.model.IModelContract.*

interface IPresentationContract {


    /**
     * This interface must be implemented by every presenter in the app
     * It will be the communication contract with the view controller in order to avoid concrete dependency and coupling
     * Its lifecycle must be bound with its view's lifecycle in order to avoid leaking and memory problems
     * @see IViewController for more information
     *
     * @see onDestroy: when this method is called, all data-sources must be released
     * @see IDataSource for more information
     *
     * @see com.tamimattafi.mvp.presentation.presenters.BasePresenter for implementation info
     *
     */
    interface IPresenter<V: IViewController> {


        /**
         * This method will be called when the view controller is paused
         * For safest usage, this method should stop any interaction with the view
         *
         */
        fun onPause()


        /**
         * This method will be called when the view controller is resumed
         * Resuming interactions with the view here is safe
         *
         */
        fun onResume()


        /**
         * This method will be called when the view controlled by the view controller is destroyed
         * You must stop any interaction with the view in order to avoid NullPointerException
         *
         * @see NullPointerException for more information
         *
         */
        fun onDestroyView()


        /**
         * This method will be called when the view controller is destroyed
         * You must stop any interaction or data processing and release all data sources and call-backs
         *
         * @see IDataSource.release,
         * @see ICallback.cancel and
         * @see ICallback.clearListeners for more information
         *
         */
        fun onDestroy()


    }






    /**
     * This interface should be implemented by every view controller such as fragments or activity
     * It will be the communication contract with the presenter in order to avoid concrete dependency and coupling
     *
     * @see IPresenter for more information
     *
     */
    interface IViewController {


        /**
         * Shows a message sent by the presenter such ass success or failure messages
         * The implementation of this method is optional
         *
         * @param message: A message of type string sent by the presenter
         *
         * @see IPresenter for more information
         *
         */
        fun showMessage(message: String) {
            Log.d("IViewController", message)
        }


    }



}