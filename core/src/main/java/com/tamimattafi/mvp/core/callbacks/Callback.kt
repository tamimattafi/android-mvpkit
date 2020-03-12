package com.tamimattafi.mvp.core.callbacks

import com.tamimattafi.mvp.core.ICoreContract.*


/**
 * Implementation of ICallback<T> where 'T' is the type of result that will be returned on success
 * @see ICallback for more information
 *
 * @param onStart: Invoked when start method is called
 * @param onCancel: Invoked when cancel method is called
 *
 * @see start and
 * @see cancel for more information
 *
 */
open class Callback<T>(
    protected open val onStart: () -> Unit,
    protected open val onCancel: () -> Unit
) : ICallback<T> {


    /**
     * Stores all success listeners attached by addSuccessListener method
     * @see addSuccessListener for more information
     *
     */
    open val successSequence by lazy { ArrayList<(data: T) -> Unit>() }


    /**
     * Stores all failure listeners attached by addFailureListener method
     * @see addFailureListener for more information
     *
     */
    open val failureSequence by lazy { ArrayList<(error: ICallbackError) -> Unit>() }


    /**
     * Stores all complete listeners attached by addCompleteListener method
     * @see addCompleteListener for more information
     *
     */
    open val completeSequence by lazy { ArrayList<() -> Unit>() }


    /**
     * Stores all progress listeners attached by addProgressListener method
     * @see addProgressListener for more information
     *
     */
    open val progressSequence by lazy { ArrayList<(progress: Int) -> Unit>() }


    /**
     * Stores all start listeners attached by addStartListener method
     * @see addStartListener for more information
     *
     */
    open val startSequence by lazy { ArrayList<() -> Unit>() }


    /**
     * Stores all cancel listeners attached by addCancelListener method
     * @see addCancelListener for more information
     *
     */
    open val cancelSequence by lazy { ArrayList<() -> Unit>() }


    /**
     * @see ICallback.addSuccessListener for more information
     *
     */
    @Synchronized
    override fun addSuccessListener(onSuccess: (data: T) -> Unit): ICallback<T> =
        this.also { it.successSequence.add(onSuccess) }


    /**
     * @see ICallback.addFailureListener for more information
     *
     */
    @Synchronized
    override fun addFailureListener(onFailure: (error: ICallbackError) -> Unit): ICallback<T> =
        this.also { it.failureSequence.add(onFailure) }


    /**
     * @see ICallback.addCompleteListener for more information
     *
     */
    @Synchronized
    override fun addCompleteListener(onComplete: () -> Unit): ICallback<T>
            = this.also { it.completeSequence.add(onComplete) }


    /**
     * @see ICallback.addProgressListener for more information
     *
     */
    @Synchronized
    override fun addProgressListener(onProgress: (progress: Int) -> Unit): ICallback<T>
            = this.also { it.progressSequence.add(onProgress) }

    /**
     * @see ICallback.addCancelListener for more information
     *
     */
    @Synchronized
    override fun addCancelListener(onCancel: () -> Unit): ICallback<T>
            = this.also { it.cancelSequence.add(onCancel) }


    /**
     * @see ICallback.addStartListener for more information
     *
     */
    @Synchronized
    override fun addStartListener(onStart: () -> Unit): ICallback<T>
            = this.also { it.startSequence.add(onStart) }


    /**
     * @see ICallback.start for more information
     *
     */
    @Synchronized
    override fun start() {
        onStart.invoke()
    }


    /**
     * @see ICallback.cancel for more information
     *
     */
    @Synchronized
    override fun cancel() {
        onCancel.invoke()
    }


    /**
     * @see ICallback.clearListeners for more information
     *
     */
    @Synchronized
    override fun clearListeners() {
        successSequence.clear()
        failureSequence.clear()
        completeSequence.clear()
        startSequence.clear()
        cancelSequence.clear()
    }

}