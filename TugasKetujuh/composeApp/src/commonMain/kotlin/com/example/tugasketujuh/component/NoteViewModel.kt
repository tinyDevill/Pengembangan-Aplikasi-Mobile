package com.example.tugasketujuh.component

import com.example.tugasketujuh.data.NoteRepository
import com.example.tugasketujuh.settings.SettingsRepository
import com.example.tugasketujuh.settings.SortOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository,
    private val settingsRepository: SettingsRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val searchQuery = MutableStateFlow("")
    private val isLoading = MutableStateFlow(true)

    private val sourceNotes = searchQuery.flatMapLatest { query ->
        val flow = if (query.isBlank()) {
            repository.notes
        } else {
            repository.searchNotes(query)
        }

        flow
            .onStart { isLoading.value = true }
            .onEach { isLoading.value = false }
    }

    val notes: StateFlow<List<Note>> = combine(
        sourceNotes,
        settingsRepository.settingsFlow
    ) { notes, settings ->
        notes.sortedWith(settings.sortOrder.toComparator())
    }.stateIn(scope, SharingStarted.Eagerly, emptyList())

    val query: StateFlow<String> = searchQuery.asStateFlow()

    val uiState: StateFlow<NotesUiState> = combine(
        notes,
        searchQuery,
        isLoading
    ) { notes, query, loading ->
        when {
            loading -> NotesUiState.Loading
            notes.isEmpty() && query.isBlank() -> NotesUiState.Empty("There are no notes yet")
            notes.isEmpty() -> NotesUiState.Empty("No result")
            else -> NotesUiState.Content(notes)
        }
    }.stateIn(scope, SharingStarted.Eagerly, NotesUiState.Loading)

    fun setSearchQuery(value: String) {
        searchQuery.value = value
    }

    fun clearSearch() {
        searchQuery.value = ""
    }

    fun addNote(title: String, content: String) {
        scope.launch {
            repository.addNote(title = title, content = content)
        }
    }

    fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean) {
        scope.launch {
            repository.updateNote(id = id, title = title, content = content, isFavorite = isFavorite)
        }
    }

    fun deleteNote(id: Int) {
        scope.launch {
            repository.deleteNote(id = id)
        }
    }

    fun toggleFavorite(id: Int) {
        scope.launch {
            repository.toggleFavorite(id = id)
        }
    }

    fun getNoteById(id: Int): Note? = notes.value.firstOrNull { it.id == id }

    fun clear() {
        scope.cancel()
    }

    private fun SortOrder.toComparator(): Comparator<Note> = when (this) {
        SortOrder.NEWEST_FIRST -> compareByDescending<Note> { it.id }
        SortOrder.OLDEST_FIRST -> compareBy<Note> { it.id }
        SortOrder.TITLE_ASC -> compareBy { it.title.lowercase() }
        SortOrder.TITLE_DESC -> compareByDescending<Note> { it.title.lowercase() }
        SortOrder.FAVORITES_FIRST -> compareByDescending<Note> { it.isFavorite }.thenByDescending { it.id }
    }
}
