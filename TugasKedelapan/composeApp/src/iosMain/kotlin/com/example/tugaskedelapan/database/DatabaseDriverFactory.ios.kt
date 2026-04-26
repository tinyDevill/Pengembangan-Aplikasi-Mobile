package com.example.tugaskedelapan.database

actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory = IosDatabaseDriverFactory()
