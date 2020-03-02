package com.tamimattafi.mvp.core.threads

import com.tamimattafi.mvp.core.CoreContract.*


/**
 * Can be extended and injected to avoid boilerplate code of recreating async tasks
 * Note that async tasks can be executed only once
 *
 * @see SimpleAsync for more information
 *
 * <P>: Parameter type
 * <R>: Result type
 *
 */
abstract class SingleParameterAsyncHandler<P, R> {


    /**
     * This method must be overridden and must return a new SimpleAsync<P, R> each time it is called
     * @see SimpleAsync<P, R> for more information
     *
     * @param notifier: the call-back notifier that will be used to notify listeners when the work is done
     * @see ICallbackNotifier<R> for more information
     */
    abstract fun getNewAsync(notifier: ICallbackNotifier<R>): SimpleAsync<P, R>


    /**
     * This method is called from other scopes in order to create and execute a new task
     * @see getNewAsync for more information
     */
    fun excuteNewAsync(notifier: ICallbackNotifier<R>, parameter: P) {
        getNewAsync(notifier).execute(parameter)
    }

}