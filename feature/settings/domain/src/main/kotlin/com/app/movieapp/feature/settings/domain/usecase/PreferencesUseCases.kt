package com.app.movieapp.feature.settings.domain.usecase

import com.app.movieapp.common.domain.usecase.FlowUseCase
import com.app.movieapp.common.domain.usecase.UseCase
import com.app.movieapp.core.contract.preferences.AppLanguage
import com.app.movieapp.core.contract.preferences.DynamicColor
import com.app.movieapp.core.contract.preferences.ThemeMode
import com.app.movieapp.core.contract.preferences.UserPreferences
import com.app.movieapp.core.contract.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObservePreferencesUseCase(
    private val repository: UserPreferencesRepository,
) : FlowUseCase<Unit, UserPreferences> {
    override fun invoke(params: Unit): Flow<UserPreferences> = repository.preferences
}

class SetThemeModeUseCase(private val repository: UserPreferencesRepository) :
    UseCase<ThemeMode, Unit> {
    override suspend fun invoke(params: ThemeMode) = repository.setThemeMode(params)
}

class SetLanguageUseCase(private val repository: UserPreferencesRepository) :
    UseCase<AppLanguage, Unit> {
    override suspend fun invoke(params: AppLanguage) = repository.setLanguage(params)
}

class SetDynamicColorUseCase(private val repository: UserPreferencesRepository) :
    UseCase<DynamicColor, Unit> {
    override suspend fun invoke(params: DynamicColor) = repository.setDynamicColor(params)
}
