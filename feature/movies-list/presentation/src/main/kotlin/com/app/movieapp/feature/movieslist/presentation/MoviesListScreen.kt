package com.app.movieapp.feature.movieslist.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.app.movieapp.common.presentation.error.toUserMessage
import com.app.movieapp.core.designsystem.component.EmptyState
import com.app.movieapp.core.designsystem.component.ErrorState
import com.app.movieapp.core.designsystem.component.OfflineBanner
import com.app.movieapp.core.designsystem.component.PosterCard
import com.app.movieapp.core.designsystem.component.PosterGridPlaceholder
import com.app.movieapp.core.navigation.moviePosterKey
import com.app.movieapp.core.navigation.sharedMovieElement
import com.app.movieapp.feature.movieslist.presentation.component.CategoryTabs
import com.app.movieapp.feature.movieslist.presentation.component.HeroCarousel
import com.app.movieapp.feature.movieslist.presentation.model.MovieUiModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val HERO_COUNT = 6

private enum class MoviesPhase { Loading, Error, Empty, Content }

@Composable
fun MoviesListRoute(
    onOpenDetails: (Int) -> Unit,
    viewModel: MoviesListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies = viewModel.pagedMovies.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MoviesListEffect.NavigateToDetails -> onOpenDetails(effect.movieId)
                is MoviesListEffect.ShowSnackbar ->
                    scope.launch { snackbarHostState.showSnackbar(effect.message) }
            }
        }
    }

    MoviesListScreen(
        state = state,
        movies = movies,
        snackbarHostState = snackbarHostState,
        onIntent = viewModel::sendIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoviesListScreen(
    state: MoviesListState,
    movies: LazyPagingItems<MovieUiModel>,
    snackbarHostState: SnackbarHostState,
    onIntent: (MoviesListIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.movies_title),
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (state.isOffline) {
                OfflineBanner(message = stringResource(R.string.movies_offline))
            }

            CategoryTabs(
                selected = state.selectedCategory,
                onSelect = { onIntent(MoviesListIntent.SelectCategory(it)) },
            )

            val refresh = movies.loadState.refresh

            var userRefreshing by rememberSaveable { mutableStateOf(false) }
            LaunchedEffect(refresh) {
                if (userRefreshing && refresh !is LoadState.Loading) userRefreshing = false
            }

            val phase = when {
                refresh is LoadState.Loading && movies.itemCount == 0 -> MoviesPhase.Loading
                refresh is LoadState.Error && movies.itemCount == 0 -> MoviesPhase.Error
                refresh is LoadState.NotLoading && movies.itemCount == 0 -> MoviesPhase.Empty
                else -> MoviesPhase.Content
            }

            Crossfade(targetState = phase, label = "moviesPhase") { p ->
                when (p) {
                    MoviesPhase.Loading -> PosterGridPlaceholder(minItemWidth = 160.dp, count = 12)

                    MoviesPhase.Error -> ErrorState(
                        message = (refresh as? LoadState.Error)?.error.toUserMessage(
                            fallback = stringResource(R.string.movies_error),
                        ),
                        retryLabel = stringResource(R.string.movies_retry),
                        onRetry = { movies.retry() },
                    )

                    MoviesPhase.Empty -> EmptyState(
                        message = stringResource(R.string.movies_empty),
                    )

                    MoviesPhase.Content -> PullToRefreshBox(
                        isRefreshing = userRefreshing,
                        onRefresh = {
                            userRefreshing = true
                            movies.refresh()
                        },
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        MoviesGrid(movies = movies, onIntent = onIntent)
                    }
                }
            }
        }
    }
}

@Composable
private fun MoviesGrid(
    movies: LazyPagingItems<MovieUiModel>,
    onIntent: (MoviesListIntent) -> Unit,
) {
    val heroItems = remember(movies.itemSnapshotList) {
        (0 until minOf(HERO_COUNT, movies.itemCount)).mapNotNull { movies.peek(it) }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        if (heroItems.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    HeroCarousel(
                        movies = heroItems,
                        onClick = { onIntent(MoviesListIntent.OpenDetails(it)) },
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }
        }

        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id },
        ) { index ->
            movies[index]?.let { movie ->
                PosterCard(
                    posterUrl = movie.posterUrl,
                    title = movie.title,
                    ratingLabel = movie.ratingLabel,
                    isFavorite = movie.isFavorite,
                    onToggleFavorite = { onIntent(MoviesListIntent.ToggleFavorite(movie.id)) },
                    onClick = { onIntent(MoviesListIntent.OpenDetails(movie.id)) },

                    modifier = Modifier
                        .animateItem()
                        .sharedMovieElement(moviePosterKey(movie.id)),
                )
            }
        }
    }
}
