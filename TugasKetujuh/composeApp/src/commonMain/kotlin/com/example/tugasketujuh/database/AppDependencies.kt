package com.example.tugasketujuh.database

object AppDependencies {
    lateinit var databaseDriverFactory: DatabaseDriverFactory

    fun requireDatabaseDriverFactory(): DatabaseDriverFactory {
        return if (::databaseDriverFactory.isInitialized) {
            databaseDriverFactory
        } else {
            error("DatabaseDriverFactory belum diinisialisasi. Set dari platform entrypoint dulu.")
        }
    }
}
