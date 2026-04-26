package com.example.tugasketujuh.data

import com.example.tugasketujuh.component.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>

    fun searchNotes(query: String): Flow<List<Note>>

    suspend fun addNote(title: String, content: String)
    suspend fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean)
    suspend fun deleteNote(id: Int)
    suspend fun toggleFavorite(id: Int)
    suspend fun getNoteById(id: Int): Note?
}
