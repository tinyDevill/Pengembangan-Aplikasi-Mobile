package com.example.tugaskesepuluh.database

import app.cash.sqldelight.driver.native.NativeSqliteDriver

class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver() = NativeSqliteDriver(
        schema = NotesDatabase.Schema,
        name = "notes.db"
    )
}
