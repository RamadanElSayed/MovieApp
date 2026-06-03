package com.app.movieapp.core.network

/** Network configuration sourced from BuildConfig (populated from local.properties at build time). */
object NetworkConstants {
    val BASE_URL: String = BuildConfig.TMDB_BASE_URL
    val IMAGE_BASE_URL: String = BuildConfig.TMDB_IMAGE_BASE_URL
    val ACCESS_TOKEN: String = BuildConfig.TMDB_ACCESS_TOKEN

    /** False when no TMDB token was supplied in local.properties — every API call will 401. */
    val HAS_ACCESS_TOKEN: Boolean = ACCESS_TOKEN.isNotBlank()

    /** Builds a full poster URL from a TMDB `poster_path` and a size bucket (e.g. "w500"). */
    fun imageUrl(path: String?, size: String = "w500"): String =
        path?.takeIf { it.isNotBlank() }?.let { "$IMAGE_BASE_URL$size$it" }.orEmpty()
}
