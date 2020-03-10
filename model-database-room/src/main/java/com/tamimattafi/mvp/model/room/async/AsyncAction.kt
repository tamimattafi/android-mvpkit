package com.tamimattafi.mvp.model.room.async

data class AsyncAction<T>(
    val action: Int,
    val data: T
)