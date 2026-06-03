package com.app.movieapp.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

/**
 * A heart that springs/pops when toggled and cross-fades its tint. Skips the bounce on first
 * composition so it doesn't animate just from appearing on screen.
 */
@Composable
fun AnimatedFavoriteIcon(
    isFavorite: Boolean,
    favoriteTint: Color,
    idleTint: Color,
    modifier: Modifier = Modifier,
) {
    val scale = remember { Animatable(1f) }
    // Plain holder (not snapshot state) so flipping it never triggers a recomposition.
    val isFirst = remember { booleanArrayOf(true) }

    LaunchedEffect(isFavorite) {
        if (isFirst[0]) {
            isFirst[0] = false
            return@LaunchedEffect
        }
        // Pop out, then settle with a gentle bounce.
        scale.animateTo(1.35f, spring(stiffness = Spring.StiffnessHigh))
        scale.animateTo(
            1f,
            spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        )
    }

    val tint by animateColorAsState(
        targetValue = if (isFavorite) favoriteTint else idleTint,
        label = "favoriteTint",
    )

    Icon(
        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
        contentDescription = null,
        tint = tint,
        modifier = modifier.scale(scale.value),
    )
}
