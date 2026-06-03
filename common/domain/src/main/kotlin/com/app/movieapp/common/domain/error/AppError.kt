package com.app.movieapp.common.domain.error

/**
 * The single, typed error vocabulary for the whole app.
 *
 * No raw [Throwable], HTTP status code or SQL error ever crosses above the data layer — the data
 * layer maps everything into one of these cases (see `core:common-data`'s `safeApiCall`), and the
 * presentation layer maps these into localized strings (see `core:common-presentation`).
 */
sealed interface AppError {
    /** No connectivity / DNS failure / generic IO failure. */
    data object Network : AppError

    /** A request exceeded the configured timeout. */
    data object Timeout : AppError

    /** 401/403 — credentials missing, invalid or expired. */
    data object Unauthorized : AppError

    /** 404 — the requested resource does not exist. */
    data object NotFound : AppError

    /** Any other non-success HTTP status. */
    data class Http(val code: Int) : AppError

    /** A local persistence failure (Room read/write). */
    data object Database : AppError

    /** Anything unexpected; carries the original cause for logging/crash reporting only. */
    data class Unknown(val cause: Throwable? = null) : AppError
}
