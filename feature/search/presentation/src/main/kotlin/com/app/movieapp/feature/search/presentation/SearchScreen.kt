package com.app.movieapp.feature.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.movieapp.core.designsystem.component.EmptyState
import com.app.movieapp.core.designsystem.component.ErrorState
import com.app.movieapp.core.designsystem.component.PosterCard
import com.app.movieapp.core.designsystem.component.PosterGridPlaceholder
import com.app.movieapp.core.designsystem.theme.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchRoute(
    onOpenDetails: (Int) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchEffect.NavigateToDetails -> onOpenDetails(effect.movieId)
            }
        }
    }
    SearchScreen(state = state, onIntent = viewModel::sendIntent)
}

@Composable
internal fun SearchScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
) {
    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = state.query,
                onValueChange = { onIntent(SearchIntent.QueryChanged(it)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (state.query.isNotEmpty()) {
                        IconButton(onClick = { onIntent(SearchIntent.QueryChanged("")) }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = stringResource(R.string.search_clear),
                            )
                        }
                    }
                },
                placeholder = { Text(stringResource(R.string.search_hint)) },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.md),
            )

            when {
                state.isLoading -> PosterGridPlaceholder()
                state.error != null -> ErrorState(
                    message = state.error,
                    retryLabel = stringResource(R.string.search_retry),
                    onRetry = { onIntent(SearchIntent.Retry) },
                )
                state.showEmpty -> EmptyState(message = stringResource(R.string.search_no_results))
                state.results.isEmpty() -> EmptyState(message = stringResource(R.string.search_prompt))
                else -> LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.results, key = { it.id }) { movie ->
                        PosterCard(
                            posterUrl = movie.posterUrl,
                            title = movie.title,
                            ratingLabel = movie.ratingLabel,
                            onClick = { onIntent(SearchIntent.OpenDetails(movie.id)) },
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }
    }
}
