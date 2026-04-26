package com.example.tugaskedelapan.component

sealed interface NotesUiState {
    data object Loading : NotesUiState
    data class Empty(val message: String) : NotesUiState
    data class Content(val notes: List<Note>) : NotesUiState
}
