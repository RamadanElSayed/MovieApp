package com.app.movieapp.common.presentation.util

import java.util.Locale

/**
 * Formats a TMDB vote average as a one-decimal rating label (e.g. `8.3`). Centralized so every
 * feature's UI mapper formats ratings identically. Uses [Locale.US] for a stable decimal point.
 */
fun Double.toRatingLabel(): String = String.format(Locale.US, "%.1f", this)
