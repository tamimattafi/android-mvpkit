package com.tamimattafi.mvp.core.threads

import java.lang.Exception

data class AsyncResult<R>(
    val result: R? = null,
    val exception: Exception? = null
)