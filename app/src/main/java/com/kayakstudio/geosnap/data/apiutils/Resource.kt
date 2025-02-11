package com.kayakstudio.geosnap.data.apiutils

fun <T, R> Resource<List<T>>.mapListTo(transform: (T) -> R): Resource<List<R>> =
    when (this.status) {
        Status.SUCCESS -> Resource.success(data?.map { transform(it) })
        Status.ERROR -> Resource.error(this.message ?: "", null)
        Status.LOADING -> Resource.loading(data?.map { transform(it) })
    }

suspend fun <T, R> Resource<List<T>>.mapListToSuspend(transform: suspend (T) -> R): Resource<List<R>> =
    when (this.status) {
        Status.SUCCESS -> Resource.success(data?.map { transform(it) })
        Status.ERROR -> Resource.error(this.message ?: "", null)
        Status.LOADING -> Resource.loading(data?.map { transform(it) })
    }

fun <T, R> Resource<T>.mapTo(transform: (T?) -> R) =
    when (this.status) {
        Status.SUCCESS -> Resource.success(transform(data))
        Status.ERROR -> Resource.error(this.message ?: "", null)
        Status.LOADING -> Resource.loading(transform(data))
    }

suspend fun <T, R> Resource<T>.mapToSuspend(transform: suspend (T?) -> R) =
    when (this.status) {
        Status.SUCCESS -> Resource.success(transform(data))
        Status.ERROR -> Resource.error(this.message ?: "", null)
        Status.LOADING -> Resource.loading(transform(data))
    }

suspend fun <T, R> Resource<T>.mapToSuspendWithError(transform: suspend (T?) -> MapToSuspendResult<R>): Resource<R> {
    return when (this.status) {
        Status.SUCCESS -> {
            val mapToSuspendResult = transform(data)
            if (mapToSuspendResult.isInError) {
                Resource.error(mapToSuspendResult.errorMessage ?: "An error occurred", null)
            } else {
                Resource.success(mapToSuspendResult.result)
            }
        }

        Status.ERROR -> Resource.error(this.message ?: "", null)
        Status.LOADING -> {
            val mapToSuspendResult = transform(data)
            Resource.loading(mapToSuspendResult.result)
        }
    }
}

class MapToSuspendResult<R>(
    val result: R,
    val isInError: Boolean = false,
    val errorMessage: String? = null,
)

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(var status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
            )
        }

        fun <T> error(
            msg: String?,
            data: T? = null,
        ): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
            )
        }
    }
}
