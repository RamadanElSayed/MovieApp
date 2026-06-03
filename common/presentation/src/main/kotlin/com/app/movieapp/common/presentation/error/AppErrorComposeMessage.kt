package com.app.movieapp.common.presentation.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.domain.error.AppErrorException
import com.app.movieapp.common.presentation.R

/**
 * Composable twin of [toMessage] for places that have a `@Composable` scope (and a `Throwable`)
 * rather than a [ResourceProvider] — e.g. mapping a Paging `LoadState.Error` to display text.
 */
@Composable
fun AppError.asMessage(): String = when (this) {
    AppError.Network -> stringResource(R.string.error_no_connection)
    AppError.Timeout -> stringResource(R.string.error_timeout)
    AppError.Unauthorized -> stringResource(R.string.error_unauthorized)
    AppError.NotFound -> stringResource(R.string.error_not_found)
    AppError.Database -> stringResource(R.string.error_database)
    is AppError.Http -> stringResource(R.string.error_server, code)
    is AppError.Unknown -> stringResource(R.string.error_generic)
}

/**
 * Best-effort localized text for any [Throwable]. Unwraps an [AppErrorException] (as produced by the
 * data layer / RemoteMediator) into its precise reason, falling back to [fallback] otherwise.
 */
@Composable
fun Throwable?.toUserMessage(fallback: String): String =
    (this as? AppErrorException)?.error?.asMessage() ?: fallback
