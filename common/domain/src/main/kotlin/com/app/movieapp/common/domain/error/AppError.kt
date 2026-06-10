package com.app.movieapp.common.domain.error

sealed interface AppError {
    data object Network : AppError

    data object Timeout : AppError

    data object Unauthorized : AppError

    data object NotFound : AppError

    data class Http(
        val code: Int,
    ) : AppError

    data object Database : AppError

    data class Unknown(
        val cause: Throwable? = null,
    ) : AppError
}
