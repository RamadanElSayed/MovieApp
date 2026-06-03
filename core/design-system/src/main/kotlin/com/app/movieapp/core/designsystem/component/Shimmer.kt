package com.app.movieapp.core.designsystem.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

/**
 * An animated, theme-aware shimmer [Brush] for loading placeholders. Sweeps a soft highlight across
 * a [MaterialTheme.colorScheme.surfaceVariant] base — far classier than a spinner for content grids.
 */
@Composable
fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 1100), RepeatMode.Restart),
        label = "shimmer-translate",
    )
    val base = MaterialTheme.colorScheme.surfaceVariant
    val colors = listOf(
        base.copy(alpha = 0.85f),
        base.copy(alpha = 0.35f),
        base.copy(alpha = 0.85f),
    )
    return Brush.linearGradient(
        colors = colors,
        start = Offset(translate - 400f, translate - 400f),
        end = Offset(translate, translate),
    )
}

/** A rectangular shimmering placeholder block. Give it a fixed/aspect-ratio size via [modifier]. */
@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(rememberShimmerBrush()))
}
