package com.app.movieapp.feature.settings.presentation

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.movieapp.core.contract.preferences.AppLanguage
import com.app.movieapp.core.contract.preferences.DynamicColor
import com.app.movieapp.core.contract.preferences.ThemeMode
import com.app.movieapp.core.designsystem.theme.spacing
import androidx.compose.foundation.layout.Row
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoute(viewModel: SettingsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SettingsEffect.ApplyLanguage ->
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(effect.tag),
                    )
            }
        }
    }
    SettingsScreen(state = state, onIntent = viewModel::sendIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.settings_title)) }) },
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(MaterialTheme.spacing.md)) {
            SectionHeader(stringResource(R.string.settings_theme))
            ThemeMode.entries.forEach { mode ->
                RadioRow(
                    label = stringResource(themeLabel(mode)),
                    selected = state.preferences.themeMode == mode,
                    onSelect = { onIntent(SettingsIntent.SelectTheme(mode)) },
                )
            }

            HorizontalDivider(Modifier.padding(vertical = MaterialTheme.spacing.md))

            SectionHeader(stringResource(R.string.settings_language))
            AppLanguage.entries.forEach { language ->
                RadioRow(
                    label = stringResource(languageLabel(language)),
                    selected = state.preferences.language == language,
                    onSelect = { onIntent(SettingsIntent.SelectLanguage(language)) },
                )
            }

            HorizontalDivider(Modifier.padding(vertical = MaterialTheme.spacing.md))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.settings_dynamic_color),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = state.preferences.dynamicColor == DynamicColor.ENABLED,
                    onCheckedChange = { enabled ->
                        onIntent(
                            SettingsIntent.SelectDynamicColor(
                                if (enabled) DynamicColor.ENABLED else DynamicColor.DISABLED,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = MaterialTheme.spacing.sm),
    )
}

@Composable
private fun RadioRow(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onSelect)
            .padding(vertical = MaterialTheme.spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(label, modifier = Modifier.padding(start = MaterialTheme.spacing.sm))
    }
}

private fun themeLabel(mode: ThemeMode) = when (mode) {
    ThemeMode.LIGHT -> R.string.settings_theme_light
    ThemeMode.DARK -> R.string.settings_theme_dark
    ThemeMode.SYSTEM -> R.string.settings_theme_system
}

private fun languageLabel(language: AppLanguage) = when (language) {
    AppLanguage.ENGLISH -> R.string.settings_language_en
    AppLanguage.ARABIC -> R.string.settings_language_ar
}
