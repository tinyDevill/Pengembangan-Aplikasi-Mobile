package com.example.tugasketujuh.db

import com.example.tugasketujuh.db.NotesDatabase

class Database(driverFactory: DatabaseDriverFactory) {
    val db = NotesDatabase(driverFactory.createDriver())
}