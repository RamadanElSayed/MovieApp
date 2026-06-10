package com.app.movieapp.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MoviesList : NavKey

@Serializable
data class MovieDetails(val id: Int) : NavKey

@Serializable
data object Search : NavKey

@Serializable
data object Favorites : NavKey

@Serializable
data object Settings : NavKey
