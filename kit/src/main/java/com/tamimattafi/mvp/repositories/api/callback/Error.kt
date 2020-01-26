package com.tamimattafi.mvp.repositories.api.callback

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("message")
    val message: String
)
