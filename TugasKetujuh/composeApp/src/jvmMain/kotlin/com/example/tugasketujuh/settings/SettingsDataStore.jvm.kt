package com.example.tugasketujuh.settings

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import java.io.File

actual fun createSettingsDataStore(): DataStore<Preferences> {
    return DataStoreFactory.create(
        storage = FileStorage(
            serializer = PreferencesSerializer,
            produceFile = { File(System.getProperty("java.io.tmpdir"), SETTINGS_DATASTORE_FILE_NAME) }
        )
    )
}
