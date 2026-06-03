package com.app.movieapp.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.Scene

/** Duration of screen-to-screen navigation transitions. */
private const val NAV_ANIM_MS = 380

private typealias NavTransitionScope = AnimatedContentTransitionScope<Scene<NavKey>>

/** Slide + fade toward [direction] — shared by the forward (push) and back (pop) transitions. */
private fun NavTransitionScope.slideFade(direction: SlideDirection): ContentTransform =
    (slideIntoContainer(direction, tween(NAV_ANIM_MS)) + fadeIn(tween(NAV_ANIM_MS))) togetherWith
        (slideOutOfContainer(direction, tween(NAV_ANIM_MS)) + fadeOut(tween(NAV_ANIM_MS)))

/** Forward navigation: the new screen slides in from the end (RTL-aware). */
internal val navForwardSpec: NavTransitionScope.() -> ContentTransform =
    { slideFade(SlideDirection.Start) }

/** Back / pop: the reverse direction. */
internal val navPopSpec: NavTransitionScope.() -> ContentTransform =
    { slideFade(SlideDirection.End) }

/** Predictive back (edge-swipe): the incoming page eases in while the top scales away. */
internal val navPredictivePopSpec: NavTransitionScope.(Int) -> ContentTransform = {
    (slideIntoContainer(SlideDirection.End, tween(NAV_ANIM_MS)) + fadeIn(tween(NAV_ANIM_MS))) togetherWith
        (scaleOut(targetScale = 0.85f) + fadeOut(tween(NAV_ANIM_MS)))
}
