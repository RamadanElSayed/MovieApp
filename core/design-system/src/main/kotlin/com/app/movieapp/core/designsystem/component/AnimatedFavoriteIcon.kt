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

@Composable
fun AnimatedFavoriteIcon(
    isFavorite: Boolean,
    favoriteTint: Color,
    idleTint: Color,
    modifier: Modifier = Modifier,
) {
    val scale = remember { Animatable(1f) }

    val isFirst = remember { booleanArrayOf(true) }

    LaunchedEffect(isFavorite) {
        if (isFirst[0]) {
            isFirst[0] = false
            return@LaunchedEffect
        }

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
