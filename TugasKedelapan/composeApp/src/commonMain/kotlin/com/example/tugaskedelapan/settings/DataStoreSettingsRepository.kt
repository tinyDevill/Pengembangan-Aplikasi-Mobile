package com.example.tugaskedelapan.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreSettingsRepository(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }

    override val settingsFlow: Flow<UserSettings> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            UserSettings(
                themeMode = preferences[Keys.THEME_MODE].toEnumOrDefault(ThemeMode.SYSTEM),
                sortOrder = preferences[Keys.SORT_ORDER].toEnumOrDefault(SortOrder.NEWEST_FIRST)
            )
        }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = themeMode.name
        }
    }

    override suspend fun setSortOrder(sortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[Keys.SORT_ORDER] = sortOrder.name
        }
    }

    private inline fun <reified T : Enum<T>> String?.toEnumOrDefault(defaultValue: T): T =
        if (this.isNullOrBlank()) defaultValue else runCatching { enumValueOf<T>(this) }.getOrDefault(defaultValue)
}
