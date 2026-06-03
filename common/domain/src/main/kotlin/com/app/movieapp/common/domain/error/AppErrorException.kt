package com.app.movieapp.common.domain.error

/**
 * Carries a typed [AppError] across APIs that can only express failure as a [Throwable] — most
 * notably Paging's `RemoteMediator.MediatorResult.Error`, whose error surfaces later in the UI as
 * `LoadState.Error.error`. The presentation layer unwraps this to show a precise, localized reason
 * (e.g. "missing API key") instead of one generic message for every failure.
 */
class AppErrorException(
    val error: AppError,
    cause: Throwable? = null,
) : Exception(cause)
