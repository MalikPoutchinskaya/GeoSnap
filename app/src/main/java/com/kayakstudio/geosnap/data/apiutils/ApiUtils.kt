package com.kayakstudio.geosnap.data.apiutils

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException

suspend inline fun <reified E> safeRequest(
    apiCall: () -> HttpResponse
): ApiResponse<E> =
    try {
        val response = apiCall()
        ApiResponse.Success(response.body())
    } catch (exception: ClientRequestException) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = "Status Code: ${exception.response.status.value} - API Key Missing",
        )
    } catch (exception: HttpExceptions) {
        ApiResponse.Error.HttpError(
            code = exception.response.status.value,
            errorBody = exception.response.body(),
            errorMessage = exception.message,
        )
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError(
            message = e.message,
            errorMessage = "Something went wrong",
        )
    } catch (e: Exception) {
        ApiResponse.Error.GenericError(
            message = e.message,
            errorMessage = "Something went wrong",
        )
    }

sealed class ApiResponse<out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<E>(val response: E) : ApiResponse<E>()

    sealed class Error<E>(open val message: String?) : ApiResponse<E>() {
        /**
         * Represents server errors.
         * @param code HTTP Status code
         * @param errorBody Response body
         * @param errorMessage Custom error message
         */
        data class HttpError<E>(
            val code: Int,
            val errorBody: String?,
            val errorMessage: String?,
        ) : Error<E>(errorMessage)

        /**
         * Represent SerializationExceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class SerializationError(
            override val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>(message)

        /**
         * Represent other exceptions.
         * @param message Detail exception message
         * @param errorMessage Formatted error message
         */
        data class GenericError(
            override val message: String?,
            val errorMessage: String?,
        ) : Error<Nothing>(message)
    }
}

class HttpExceptions(
    response: HttpResponse,
    failureReason: String?,
    cachedResponseText: String,
) : ResponseException(response, cachedResponseText) {
    override val message: String = "Status: ${response.status}" + " Failure: $failureReason"
}

inline fun <T, R> ApiResponse<T>.map(
    hasBusinessError: (value: T) -> Boolean = { false },
    getBusinessErrorMessage: (value: T) -> String? = { null },
    transform: (value: T) -> R,
): Result<R> =
    when (this) {
        is ApiResponse.Success -> {
            if (hasBusinessError(response)) {
                val errorMessage = getBusinessErrorMessage(response)
                Result.failure(Throwable(errorMessage))
            } else {
                Result.success(transform(response))
            }
        }

        is ApiResponse.Error -> Result.failure(
            Exception(Throwable(message))
        )
    }