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

private const val NAV_ANIM_MS = 380

private typealias NavTransitionScope = AnimatedContentTransitionScope<Scene<NavKey>>

private fun NavTransitionScope.slideFade(direction: SlideDirection): ContentTransform =
    (slideIntoContainer(direction, tween(NAV_ANIM_MS)) + fadeIn(tween(NAV_ANIM_MS))) togetherWith
        (slideOutOfContainer(direction, tween(NAV_ANIM_MS)) + fadeOut(tween(NAV_ANIM_MS)))

internal val navForwardSpec: NavTransitionScope.() -> ContentTransform =
    { slideFade(SlideDirection.Start) }

internal val navPopSpec: NavTransitionScope.() -> ContentTransform =
    { slideFade(SlideDirection.End) }

internal val navPredictivePopSpec: NavTransitionScope.(Int) -> ContentTransform = {
    (slideIntoContainer(SlideDirection.End, tween(NAV_ANIM_MS)) + fadeIn(tween(NAV_ANIM_MS))) togetherWith
        (scaleOut(targetScale = 0.85f) + fadeOut(tween(NAV_ANIM_MS)))
}
