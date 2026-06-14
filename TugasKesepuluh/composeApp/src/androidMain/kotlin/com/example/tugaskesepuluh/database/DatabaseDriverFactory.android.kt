package com.example.tugaskesepuluh.database

actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory = AndroidDatabaseDriverFactory()
