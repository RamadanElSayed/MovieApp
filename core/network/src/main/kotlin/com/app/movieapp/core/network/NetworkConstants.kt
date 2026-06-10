package com.app.movieapp.core.network

object NetworkConstants {
    val BASE_URL: String = BuildConfig.TMDB_BASE_URL
    val IMAGE_BASE_URL: String = BuildConfig.TMDB_IMAGE_BASE_URL
    val ACCESS_TOKEN: String = BuildConfig.TMDB_ACCESS_TOKEN

    val HAS_ACCESS_TOKEN: Boolean = ACCESS_TOKEN.isNotBlank()

    fun imageUrl(path: String?, size: String = "w500"): String =
        path?.takeIf { it.isNotBlank() }?.let { "$IMAGE_BASE_URL$size$it" }.orEmpty()
}
