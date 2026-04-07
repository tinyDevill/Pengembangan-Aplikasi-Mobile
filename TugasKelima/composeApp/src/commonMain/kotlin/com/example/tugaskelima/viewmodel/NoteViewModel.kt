package com.example.tugaskelima.viewmodel

import androidx.compose.runtime.mutableStateListOf
import com.example.tugaskelima.model.Note

class NoteViewModel {

    private var nextId = 1

    var notes = mutableStateListOf<Note>()
        private set

    fun addNote(title: String, content: String) {
        notes.add(
            Note(
                id = nextId++,
                title = title,
                content = content
            )
        )
    }

    fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    fun updateNote(id: Int, title: String, content: String) {
        val note = getNoteById(id)
        note?.let {
            it.title = title
            it.content = content
        }
    }
}