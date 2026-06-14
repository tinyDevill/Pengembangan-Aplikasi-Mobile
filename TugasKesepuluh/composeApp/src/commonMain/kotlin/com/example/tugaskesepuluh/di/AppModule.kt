package com.example.tugaskesepuluh.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tugaskesepuluh.component.NoteViewModel
import com.example.tugaskesepuluh.data.NoteRepository
import com.example.tugaskesepuluh.data.SqlDelightNoteRepository
import com.example.tugaskesepuluh.database.DatabaseDriverFactory
import com.example.tugaskesepuluh.database.provideDatabaseDriverFactory
import com.example.tugaskesepuluh.device.DeviceInfo
import com.example.tugaskesepuluh.network.NetworkMonitor
import com.example.tugaskesepuluh.settings.DataStoreSettingsRepository
import com.example.tugaskesepuluh.settings.SettingsRepository
import com.example.tugaskesepuluh.settings.SettingsViewModel
import com.example.tugaskesepuluh.settings.createSettingsDataStore
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
