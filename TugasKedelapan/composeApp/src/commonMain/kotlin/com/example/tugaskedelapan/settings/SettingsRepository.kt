package com.example.tugaskedelapan.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settingsFlow: Flow<UserSettings>

    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun setSortOrder(sortOrder: SortOrder)
}
