package com.app.movieapp.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.annotation.StringRes
import com.app.movieapp.core.network.NetworkConstants
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.app.movieapp.R
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.app.movieapp.core.contract.preferences.UserPreferencesRepository
import com.app.movieapp.core.designsystem.theme.MovieAppTheme
import com.app.movieapp.core.navigation.Favorites
import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.core.navigation.LocalSharedTransitionScope
import com.app.movieapp.core.navigation.MoviesList
import com.app.movieapp.core.navigation.Search
import com.app.movieapp.core.navigation.Settings
import com.app.movieapp.core.navigation.install
import org.koin.compose.getKoin
import org.koin.compose.koinInject

private data class TopLevelDestination(
    val key: NavKey,
    val icon: ImageVector,
    @StringRes val labelRes: Int,
)

private val topLevelDestinations = listOf(
    TopLevelDestination(MoviesList, Icons.Filled.Home, R.string.nav_movies),
    TopLevelDestination(Search, Icons.Filled.Search, R.string.nav_search),
    TopLevelDestination(Favorites, Icons.Filled.Favorite, R.string.nav_favorites),
    TopLevelDestination(Settings, Icons.Filled.Settings, R.string.nav_settings),
)

/**
 * The composition root for navigation + theming.
 *
 * - Theme + dynamic color come from the persisted [UserPreferences] (observed reactively).
 * - The back stack lives HERE (single source of truth) inside ONE [NavDisplay].
 * - Every feature's [FeatureEntryProvider] is collected from Koin (`getAll`) and installed — the app
 *   never imports a feature's screens directly.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieAppRoot() {
    val preferencesRepository = koinInject<UserPreferencesRepository>()
    // null until DataStore emits the first snapshot — avoids flashing onboarding to returning users.
    val preferences by preferencesRepository.preferences
        .collectAsStateWithLifecycle(initialValue = null)

    val prefs = preferences ?: return

    MovieAppTheme(
        themeMode = prefs.themeMode,
        dynamicColor = prefs.dynamicColor,
    ) {
        val scope = rememberCoroutineScope()

        // First-launch onboarding gate: shown until the user finishes it (flag persisted in DataStore).
        if (!prefs.onboardingCompleted) {
            OnboardingScreen(
                onFinish = { scope.launch { preferencesRepository.setOnboardingCompleted(true) } },
            )
            return@MovieAppTheme
        }

        // Setup gate: without a TMDB key every request 401s, so guide the user to add one. They can
        // still continue into the app. Once the key is added + rebuilt, this never shows again.
        var setupDismissed by rememberSaveable { mutableStateOf(false) }
        if (!NetworkConstants.HAS_ACCESS_TOKEN && !setupDismissed) {
            ApiKeySetupScreen(onContinue = { setupDismissed = true })
            return@MovieAppTheme
        }

        val backStack = rememberNavBackStack(MoviesList)
        val entryProviders = getKoin().getAll<FeatureEntryProvider>()

        val currentTop = backStack.lastOrNull()
        val showBottomBar = topLevelDestinations.any { it.key == currentTop }

        Scaffold(
            // Don't consume the status-bar inset here — each screen's own Scaffold handles the top
            // inset, which lets the details backdrop draw edge-to-edge UNDER the status bar. The
            // bottom NavigationBar still consumes the bottom inset.
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        topLevelDestinations.forEach { dest ->
                            val label = stringResource(dest.labelRes)
                            NavigationBarItem(
                                selected = currentTop == dest.key,
                                onClick = {
                                    // Switch top-level tab: reset to that root destination.
                                    backStack.clear()
                                    backStack.add(dest.key)
                                },
                                icon = { Icon(dest.icon, contentDescription = label) },
                                label = { Text(label) },
                            )
                        }
                    }
                }
            },
        ) { padding ->
            // One SharedTransitionLayout around the whole NavDisplay enables shared-element morphs
            // (e.g. a tapped poster expanding into details). The scope is published via
            // LocalSharedTransitionScope so feature screens opt in without depending on each other.
            // padding reserves space for the bottom bar; consumeWindowInsets marks those insets as
            // handled so child screens' own Scaffolds don't add a SECOND bottom inset (the gap).
            SharedTransitionLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding),
            ) {
                CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        modifier = Modifier.fillMaxSize(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        transitionSpec = navForwardSpec,
                        popTransitionSpec = navPopSpec,
                        predictivePopTransitionSpec = navPredictivePopSpec,
                        entryProvider = entryProvider {
                            entryProviders.forEach { provider -> install(provider, backStack) }
                        },
                    )
                }
            }
        }
    }
}
