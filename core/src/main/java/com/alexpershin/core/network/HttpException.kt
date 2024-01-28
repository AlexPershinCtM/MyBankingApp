package com.alexpershin.core.network

sealed class StarlingException(override val message: String?, override val cause: Throwable? = null) :
    Throwable(message, cause) {

    data class HttpException(val code: Int) : StarlingException(
        message = "Request failed with HTTP code $code"
    )

    object InsufficientFunds : StarlingException(
        message = "Request failed with error: InsufficientFunds"
    )
}