package com.app.movieapp.feature.movieslist.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.app.movieapp.core.designsystem.component.PosterImage
import com.app.movieapp.core.designsystem.component.RatingBadge
import com.app.movieapp.feature.movieslist.presentation.model.MovieUiModel
import kotlin.math.absoluteValue
import kotlinx.coroutines.delay

/**
 * A featured, auto-advancing backdrop carousel for the top of the movies list. Each page is a
 * cinematic 16:9 backdrop with a scrim, title, year and rating; tapping opens details.
 */
@Composable
fun HeroCarousel(
    movies: List<MovieUiModel>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (movies.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { movies.size })

    // Auto-advance every few seconds (wraps around).
    LaunchedEffectAutoAdvance(pageCount = movies.size) {
        val next = (pagerState.currentPage + 1) % movies.size
        pagerState.animateScrollToPage(next)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 12.dp,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp),
        ) { page ->
            val movie = movies[page]
            // Distance of this page from the settled centre (0 = centered, 1 = a full page away).
            val pageOffset = ((pagerState.currentPage - page) +
                pagerState.currentPageOffsetFraction).absoluteValue
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .graphicsLayer {
                        // Neighbouring pages shrink + fade slightly for a depth/parallax feel.
                        val scale = lerp(0.88f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                        scaleX = scale
                        scaleY = scale
                        alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onClick(movie.id) },
            ) {
                PosterImage(
                    url = movie.backdropUrl.ifBlank { movie.posterUrl },
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                )
                Box(
                    Modifier.fillMaxSize().background(
                        Brush.verticalGradient(
                            0.45f to Color.Transparent,
                            1f to Color.Black.copy(alpha = 0.85f),
                        ),
                    ),
                )
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        Modifier.padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RatingBadge(ratingLabel = movie.ratingLabel)
                        if (movie.year.isNotBlank()) {
                            Text(
                                text = movie.year,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.padding(start = 12.dp),
                            )
                        }
                    }
                }
            }
        }

        // Page indicator.
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(movies.size) { index ->
                val selected = index == pagerState.currentPage
                Box(
                    Modifier
                        .padding(horizontal = 3.dp)
                        .height(6.dp)
                        .size(width = if (selected) 20.dp else 6.dp, height = 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant,
                        ),
                )
            }
        }
    }
}

/** Small helper so the auto-advance loop reads cleanly at the call-site. */
@Composable
private fun LaunchedEffectAutoAdvance(pageCount: Int, advance: suspend () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(pageCount) {
        if (pageCount <= 1) return@LaunchedEffect
        while (true) {
            delay(4500)
            advance()
        }
    }
}
