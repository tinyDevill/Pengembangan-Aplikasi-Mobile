package com.example.tugasketujuh.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.tugasketujuh.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.time.Clock

class NoteRepository(private val database: Database) {

    private val queries = database.db.noteQueries

    fun getAllNotes(): Flow<List<com.example.tugasketujuh.db.Note>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun search(query: String): Flow<List<com.example.tugasketujuh.db.Note>> {
        val q = "%$query%"
        return queries.search(q, q)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun insert(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.insert(title, content, now)
    }

    suspend fun update(id: Long, title: String, content: String, isFavorite: Boolean = false) {
        val favoriteInt : Long = if (isFavorite) 1 else 0
        queries.update(title, content, favoriteInt, id)
    }

    suspend fun delete(id: Long) {
        queries.delete(id)
    }

    suspend fun getNoteById(id: Long): com.example.tugasketujuh.db.Note? {
        return withContext(Dispatchers.IO) {
            queries.selectById(id).executeAsOneOrNull()
        }
    }

    suspend fun toggleFavorite(id: Long) {
        val note = getNoteById(id) ?: return
        val newIsFavorite = !note.is_favorite
        update(id, note.title, note.content, newIsFavorite)
    }
}