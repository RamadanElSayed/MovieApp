package com.app.movieapp.common.domain.error

class AppErrorException(
    val error: AppError,
    cause: Throwable? = null,
) : Exception(cause)
