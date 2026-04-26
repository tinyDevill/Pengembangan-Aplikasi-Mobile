package com.example.tugasketujuh.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val settings = repository.settingsFlow.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = UserSettings()
    )

    fun setThemeMode(themeMode: ThemeMode) {
        scope.launch {
            repository.setThemeMode(themeMode)
        }
    }

    fun setSortOrder(sortOrder: SortOrder) {
        scope.launch {
            repository.setSortOrder(sortOrder)
        }
    }

    fun clear() {
        scope.cancel()
    }
}
