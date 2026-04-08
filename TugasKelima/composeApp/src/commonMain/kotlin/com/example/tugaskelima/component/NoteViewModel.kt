package com.example.tugaskelima.component

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NoteViewModel : ViewModel() {
    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

    init {
        _notes.addAll(
            listOf(
                Note(1, "Belajar Compose", "Multiplatform navigation sangat keren!", false),
                Note(2, "Tugas Kuliah", "Deadline minggu ini", true),
                Note(3, "Meeting Proyek", "Jam 14:00 dengan tim", false)
            )
        )
    }

    fun addNote(title: String, content: String) {
        val newId = (_notes.maxOfOrNull { it.id } ?: 0) + 1
        _notes.add(Note(newId, title, content))
    }

    fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean) {
        val index = _notes.indexOfFirst { it.id == id }
        if (index != -1) {
            _notes[index] = Note(id, title, content, isFavorite)
        }
    }

    fun toggleFavorite(id: Int) {
        val index = _notes.indexOfFirst { it.id == id }
        if (index != -1) {
            val note = _notes[index]
            _notes[index] = note.copy(isFavorite = !note.isFavorite)
        }
    }

    fun getNoteById(id: Int): Note? = _notes.find { it.id == id }
}