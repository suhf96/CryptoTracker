package com.gyadam.cryptotracker.core.presentation.util

import android.content.Context
import com.gyadam.cryptotracker.R
import com.gyadam.cryptotracker.core.domain.util.NetworkError
import kotlin.concurrent.thread

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_request
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.SERIALIZATION_ERROR -> R.string.serialization_error
        NetworkError.UNKNOWN -> R.string.unknown_error
    }
    return context.getString(resId)
}