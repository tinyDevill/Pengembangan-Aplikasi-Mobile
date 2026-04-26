package com.example.tugaskedelapan.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.example.tugaskedelapan.component.Note
import com.example.tugaskedelapan.database.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightNoteRepository(
    driver: SqlDriver
) : NoteRepository {
    private val database = NotesDatabase(driver)
    private val queries = database.noteQueries

    override val notes: Flow<List<Note>> = queries
        .selectAll()
        .asFlow()
        .mapToList(Dispatchers.Default)
        .map { rows -> rows.map { row ->
            Note(
                id = row.id.toInt(),
                title = row.title,
                content = row.content,
                isFavorite = row.isFavorite != 0L
            )
        } }

    override fun searchNotes(query: String): Flow<List<Note>> = queries
        .searchNotes(query = query)
        .asFlow()
        .mapToList(Dispatchers.Default)
        .map { rows -> rows.map { row ->
            Note(
                id = row.id.toInt(),
                title = row.title,
                content = row.content,
                isFavorite = row.isFavorite != 0L
            )
        } }

    override suspend fun addNote(title: String, content: String) {
        queries.insertNote(title = title, content = content, isFavorite = 0L)
    }

    override suspend fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean) {
        queries.updateNote(
            title = title,
            content = content,
            isFavorite = if (isFavorite) 1L else 0L,
            id = id.toLong()
        )
    }

    override suspend fun deleteNote(id: Int) {
        queries.deleteNote(id = id.toLong())
    }

    override suspend fun toggleFavorite(id: Int) {
        queries.toggleFavorite(id = id.toLong())
    }

    override suspend fun getNoteById(id: Int): Note? {
        return queries.selectById(id = id.toLong())
            .executeAsOneOrNull()
            ?.let { row ->
                Note(
                    id = row.id.toInt(),
                    title = row.title,
                    content = row.content,
                    isFavorite = row.isFavorite != 0L
                )
            }
    }

}
