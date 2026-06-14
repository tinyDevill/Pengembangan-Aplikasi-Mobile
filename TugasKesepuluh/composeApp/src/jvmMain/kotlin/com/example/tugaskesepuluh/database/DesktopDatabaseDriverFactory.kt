package com.example.tugaskesepuluh.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

class DesktopDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver() = JdbcSqliteDriver(
        url = "jdbc:sqlite:notes.db",
        properties = Properties(),
        schema = NotesDatabase.Schema
    )
}
