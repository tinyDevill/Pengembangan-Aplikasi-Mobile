package com.example.tugasketujuh.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal const val SETTINGS_DATASTORE_FILE_NAME = "settings.preferences_pb"

expect fun createSettingsDataStore(): DataStore<Preferences>
