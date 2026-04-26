package com.example.tugaskedelapan.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tugaskedelapan.component.NoteViewModel
import com.example.tugaskedelapan.data.NoteRepository
import com.example.tugaskedelapan.data.SqlDelightNoteRepository
import com.example.tugaskedelapan.database.DatabaseDriverFactory
import com.example.tugaskedelapan.database.provideDatabaseDriverFactory
import com.example.tugaskedelapan.device.DeviceInfo
import com.example.tugaskedelapan.network.NetworkMonitor
import com.example.tugaskedelapan.settings.DataStoreSettingsRepository
import com.example.tugaskedelapan.settings.SettingsRepository
import com.example.tugaskedelapan.settings.SettingsViewModel
import com.example.tugaskedelapan.settings.createSettingsDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single<DatabaseDriverFactory> { provideDatabaseDriverFactory() }
    single<DataStore<Preferences>> { createSettingsDataStore() }
    single<NoteRepository> { SqlDelightNoteRepository(get<DatabaseDriverFactory>().createDriver()) }
    single<SettingsRepository> { DataStoreSettingsRepository(get()) }
    single { DeviceInfo() }
    single { NetworkMonitor() }
    factory { NoteViewModel(get(), get()) }
    factory { SettingsViewModel(get()) }
}
