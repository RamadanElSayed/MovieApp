package com.app.movieapp.feature.movieslist.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.movieapp.feature.movieslist.domain.model.MovieCategory
import com.app.movieapp.feature.movieslist.presentation.R

/** Horizontally-scrolling category selector (Popular / Now Playing / Top Rated / Upcoming). */
@Composable
fun CategoryTabs(
    selected: MovieCategory,
    onSelect: (MovieCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(MovieCategory.entries) { category ->
            val isSelected = category == selected
            // Animate elevation so the active chip gently lifts.
            val elevation by animateDpAsState(if (isSelected) 4.dp else 0.dp, label = "chipElevation")
            val labelColor by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurfaceVariant,
                label = "chipLabel",
            )
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(category) },
                label = { Text(category.label(), color = labelColor) },
                shape = MaterialTheme.shapes.large,
                elevation = FilterChipDefaults.filterChipElevation(elevation = elevation),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

/** Localized label for a [MovieCategory]. */
@Composable
private fun MovieCategory.label(): String = stringResource(
    when (this) {
        MovieCategory.POPULAR -> R.string.movies_cat_popular
        MovieCategory.NOW_PLAYING -> R.string.movies_cat_now_playing
        MovieCategory.TOP_RATED -> R.string.movies_cat_top_rated
        MovieCategory.UPCOMING -> R.string.movies_cat_upcoming
    },
)
