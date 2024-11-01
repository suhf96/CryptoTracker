package com.gyadam.cryptotracker.core.data.networking

import com.gyadam.cryptotracker.core.domain.util.NetworkError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import com.gyadam.cryptotracker.core.domain.util.Result.Success
import com.gyadam.cryptotracker.core.domain.util.Result.Error
import com.gyadam.cryptotracker.core.domain.util.Result

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Error(NetworkError.SERIALIZATION_ERROR)
            }
        }

        408 -> Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Error(NetworkError.SERVER_ERROR)
        else -> Error(NetworkError.UNKNOWN)
    }
}