package com.app.movieapp.common.presentation.error

import com.app.movieapp.common.domain.error.AppError
import com.app.movieapp.common.presentation.R
import com.app.movieapp.common.presentation.resource.ResourceProvider

fun AppError.toMessage(res: ResourceProvider): String = when (this) {
    AppError.Network -> res.getString(R.string.error_no_connection)
    AppError.Timeout -> res.getString(R.string.error_timeout)
    AppError.Unauthorized -> res.getString(R.string.error_unauthorized)
    AppError.NotFound -> res.getString(R.string.error_not_found)
    AppError.Database -> res.getString(R.string.error_database)
    is AppError.Http -> res.getString(R.string.error_server, code)
    is AppError.Unknown -> res.getString(R.string.error_generic)
}
