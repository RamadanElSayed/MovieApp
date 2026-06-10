package com.app.movieapp.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PosterGridPlaceholder(
    modifier: Modifier = Modifier,
    minItemWidth: Dp = 150.dp,
    count: Int = 9,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = minItemWidth),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = false,
        modifier = modifier.fillMaxSize(),
    ) {
        items(count) { PosterCardPlaceholder() }
    }
}
