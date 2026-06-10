package com.app.movieapp.feature.moviedetails.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.movieapp.core.designsystem.component.AnimatedFavoriteIcon
import com.app.movieapp.core.designsystem.component.ErrorState
import com.app.movieapp.core.designsystem.component.LoadingState
import com.app.movieapp.core.designsystem.component.PosterImage
import com.app.movieapp.core.designsystem.component.RatingBadge
import com.app.movieapp.core.navigation.moviePosterKey
import com.app.movieapp.core.navigation.sharedMovieElement
import com.app.movieapp.core.designsystem.theme.spacing
import com.app.movieapp.feature.moviedetails.presentation.model.MovieDetailsUiModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailsRoute(
    movieId: Int,
    onBack: () -> Unit,

    viewModel: MovieDetailsViewModel = koinViewModel(key = "movie_details_$movieId") {
        parametersOf(movieId)
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieDetailsScreen(
        state = state,
        onBack = onBack,
        onRetry = { viewModel.sendIntent(MovieDetailsIntent.Retry) },
        onToggleFavorite = { viewModel.sendIntent(MovieDetailsIntent.ToggleFavorite) },
    )
}

private enum class DetailsPhase { Loading, Error, Content }

@Composable
internal fun MovieDetailsScreen(
    state: MovieDetailsState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onToggleFavorite: () -> Unit,
) {
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        val phase = when {
            state.isLoading -> DetailsPhase.Loading
            state.error != null -> DetailsPhase.Error
            else -> DetailsPhase.Content
        }
        Crossfade(targetState = phase, label = "detailsPhase") { p ->
            when (p) {
                DetailsPhase.Loading -> LoadingState()
                DetailsPhase.Error -> ErrorState(
                    message = state.error.orEmpty(),
                    retryLabel = stringResource(R.string.details_retry),
                    onRetry = onRetry,
                )
                DetailsPhase.Content -> state.movie?.let { movie ->
                    DetailsContent(
                        movie = movie,
                        isFavorite = state.isFavorite,
                        onToggleFavorite = onToggleFavorite,
                    )
                }
            }
        }

        CircleScrim(
            Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(MaterialTheme.spacing.sm),
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.details_back),
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
private fun DetailsContent(
    movie: MovieDetailsUiModel,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()

            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
        ) {
            PosterImage(
                url = movie.backdropUrl.ifBlank { movie.posterUrl },
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
            )
            Box(
                Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        0.4f to Color.Transparent,
                        1f to MaterialTheme.colorScheme.background,
                    ),
                ),
            )

            CircleScrim(Modifier.align(Alignment.BottomEnd).padding(MaterialTheme.spacing.md)) {
                IconButton(onClick = onToggleFavorite) {
                    AnimatedFavoriteIcon(
                        isFavorite = isFavorite,
                        favoriteTint = MaterialTheme.colorScheme.error,
                        idleTint = Color.White,
                    )
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .offset(y = (-48).dp)
                .padding(horizontal = MaterialTheme.spacing.md),
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                shadowElevation = 8.dp,
                modifier = Modifier
                    .width(110.dp)
                    .aspectRatio(2f / 3f)

                    .sharedMovieElement(moviePosterKey(movie.id)),
            ) {
                PosterImage(url = movie.posterUrl, contentDescription = movie.title)
            }
            Column(
                Modifier
                    .padding(start = MaterialTheme.spacing.md)
                    .padding(top = 56.dp),
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(Modifier.height(MaterialTheme.spacing.sm))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBadge(ratingLabel = movie.ratingLabel)
                    if (movie.year.isNotBlank()) {
                        Text(
                            text = movie.year,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = MaterialTheme.spacing.md),
                        )
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .offset(y = (-24).dp)
                .padding(horizontal = MaterialTheme.spacing.md)
                .padding(bottom = MaterialTheme.spacing.xl),
        ) {
            Text(
                text = stringResource(R.string.details_overview),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.sm))
            Text(
                text = movie.overview.ifBlank { stringResource(R.string.details_no_overview) },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CircleScrim(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
    ) { content() }
}
