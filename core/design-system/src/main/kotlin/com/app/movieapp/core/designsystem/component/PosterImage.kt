package com.app.movieapp.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter

/**
 * Image loader with graceful states: a shimmer while loading and a movie-icon fallback on
 * error/empty URL. Used by [PosterCard] and detail backdrops so imagery never "pops" or shows a
 * blank box.
 */
@Composable
fun PosterImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(modifier = modifier) {
        var state by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }

        if (state is AsyncImagePainter.State.Loading) {
            ShimmerBox(Modifier.fillMaxSize())
        }

        // Crossfade the bitmap in once it's ready instead of letting it pop.
        val imageAlpha by animateFloatAsState(
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
            animationSpec = tween(durationMillis = 350),
            label = "imageFade",
        )

        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onState = { state = it },
            modifier = Modifier.fillMaxSize().alpha(imageAlpha),
        )

        if (state is AsyncImagePainter.State.Error || url.isBlank()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Filled.Movie,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(48.dp),
                )
            }
        }
    }
}
