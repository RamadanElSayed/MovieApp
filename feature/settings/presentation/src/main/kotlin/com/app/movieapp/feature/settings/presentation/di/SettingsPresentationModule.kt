package com.app.movieapp.feature.settings.presentation.di

import com.app.movieapp.core.navigation.FeatureEntryProvider
import com.app.movieapp.feature.settings.presentation.SettingsViewModel
import com.app.movieapp.feature.settings.presentation.navigation.SettingsEntryProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val settingsPresentationModule = module {
    viewModelOf(::SettingsViewModel)
    singleOf(::SettingsEntryProvider) { bind<FeatureEntryProvider>() }
}
