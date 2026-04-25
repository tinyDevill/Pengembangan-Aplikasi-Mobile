package com.example.tugasketujuh.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasketujuh.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val notes = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                repository.getAllNotes()
            } else {
                repository.search(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSearch(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insert(title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }

    // Ambil note berdasarkan ID
    suspend fun getNoteById(id: Long): com.example.tugasketujuh.db.Note? {
        return repository.getNoteById(id)
    }

    // Toggle favorite
    fun toggleFavorite(id: Long) {
        viewModelScope.launch {
            repository.toggleFavorite(id)
        }
    }

    // Update dengan isFavorite
    fun updateNote(id: Long, title: String, content: String, isFavorite: Boolean = false) {
        viewModelScope.launch {
            repository.update(id, title, content, isFavorite)
        }
    }
}