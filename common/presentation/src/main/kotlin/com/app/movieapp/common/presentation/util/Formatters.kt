package com.app.movieapp.common.presentation.util

import java.util.Locale

fun Double.toRatingLabel(): String = String.format(Locale.US, "%.1f", this)
