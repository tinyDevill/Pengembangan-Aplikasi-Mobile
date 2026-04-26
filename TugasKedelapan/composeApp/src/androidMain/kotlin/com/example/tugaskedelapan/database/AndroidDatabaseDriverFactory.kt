package com.example.tugaskedelapan.database

import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver() = AndroidSqliteDriver(
        schema = NotesDatabase.Schema,
        context = AndroidPlatformContextHolder.context,
        name = "notes.db"
    )
}
