package com.app.movieapp.core.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.LocalNavAnimatedContentScope

/**
 * The single [SharedTransitionScope] for the app, provided at the composition root from the
 * `SharedTransitionLayout` that wraps the `NavDisplay`. Feature screens read it (together with the
 * per-entry `LocalNavAnimatedContentScope`) to morph shared elements across navigation — without
 * any feature depending on another.
 */
val LocalSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope?> =
    compositionLocalOf { null }

/** Stable shared-element key for a movie poster, so list and details agree on the same identity. */
fun moviePosterKey(movieId: Int): String = "movie-poster-$movieId"

/**
 * Tags this composable as a shared element identified by [key], so it smoothly morphs between
 * screens that use the same key (e.g. a list poster expanding into the details poster).
 *
 * Safe no-op when no shared-transition scope is in scope (so previews / tests don't break).
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedMovieElement(key: Any): Modifier {
    val sharedScope = LocalSharedTransitionScope.current ?: return this
    return with(sharedScope) {
        this@sharedMovieElement.sharedElement(
            rememberSharedContentState(key = key),
            animatedVisibilityScope = LocalNavAnimatedContentScope.current,
        )
    }
}
