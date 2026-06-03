package com.app.movieapp.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * The single place cross-feature destinations are declared. A feature can push another feature's
 * key (e.g. movies-list pushes [MovieDetails]) WITHOUT depending on that feature's module — exactly
 * the contract/dependency-inversion pattern, applied to navigation.
 *
 * All keys are @Serializable so the back stack can be persisted/restored via kotlinx.serialization.
 */
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
