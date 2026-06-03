package com.app.movieapp.feature.settings.domain.di

import com.app.movieapp.feature.settings.domain.usecase.ObservePreferencesUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetDynamicColorUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetLanguageUseCase
import com.app.movieapp.feature.settings.domain.usecase.SetThemeModeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsDomainModule = module {
    factoryOf(::ObservePreferencesUseCase)
    factoryOf(::SetThemeModeUseCase)
    factoryOf(::SetLanguageUseCase)
    factoryOf(::SetDynamicColorUseCase)
}
