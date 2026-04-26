package com.example.tugasketujuh.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import java.io.File

object AndroidSettingsContextHolder {
    lateinit var filesDir: File
}

actual fun createSettingsDataStore(): DataStore<Preferences> {
    val file = File(
        AndroidSettingsContextHolder.filesDir,
        SETTINGS_DATASTORE_FILE_NAME
    )

    return PreferenceDataStoreFactory.create(
        produceFile = { file }
    )
}