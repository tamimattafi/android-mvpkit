package com.tamimattafi.mvp.api.interactor

object InteractorConstants {


    const val HEADER_AUTH = "Authorization"
    const val HEADER_AUTH_BARER = "Bearer"

    fun getCodeMessage(code: Int): String = "$code " + when (code) {
        CODE_OK -> MESSAGE_OK
        CODE_NOT_FOUND -> MESSAGE_NOT_FOUND
        CODE_BAD_REQUEST -> MESSAGE_BAD_REQUEST
        CODE_UNAUTHORIZED -> MESSAGE_UNAUTHORIZED
        in CODE_INNER_SERVER_ERROR -> MESSAGE_INNER_SERVER_ERROR
        else -> throw IllegalArgumentException("Status code $code is unknown")
    }

    const val CODE_OK = 200
    const val CODE_NOT_FOUND = 404
    const val CODE_BAD_REQUEST = 400
    const val CODE_UNAUTHORIZED = 401
    val CODE_INNER_SERVER_ERROR = 500..559

    const val MESSAGE_OK = "Success: OK."
    const val MESSAGE_NOT_FOUND = "Error: Not found."
    const val MESSAGE_BAD_REQUEST = "Error: Bad Request."
    const val MESSAGE_UNAUTHORIZED = "Error: Unauthorized."
    const val MESSAGE_INNER_SERVER_ERROR = "Error: Inner server error."

}