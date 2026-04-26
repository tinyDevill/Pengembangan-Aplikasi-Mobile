package com.example.tugasketujuh.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {
    override fun createDriver() = AndroidSqliteDriver(
        schema = NotesDatabase.Schema,
        context = context,
        name = "notes.db"
    )
}
