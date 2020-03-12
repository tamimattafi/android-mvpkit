package com.tamimattafi.mvp.model

interface IModelContract {


    /**
     *
     * ##### TABLE OF CONTENT #####
     *
     *
     *
     * ## 1. DataSources - Data processing ##
     * @see IDataSource
     *
     *
     *
     *
     */




    /**
     * This interface must be implemented by every data-source that stores states such as repositories and interactors
     *
     * @see IPresenter for more information
     * @see IViewController for more information
     *
     * @see com.tamimattafi.mvp.core.data.BaseDataSource for implementation info
     *
     */
    interface IDataSource {


        /**
         * This method must clear all saved states and cancel all current processes if possible
         * It also must be called when data-consumers are destroyed such as presenters, or when other data-sources are released
         *
         * @see IPresenter for more information
         * @see IViewController for more information
         *
         */
        fun release()


    }



}