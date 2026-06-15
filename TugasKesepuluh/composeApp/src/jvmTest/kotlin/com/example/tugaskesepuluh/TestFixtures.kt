package com.example.tugaskesepuluh

import com.example.tugaskesepuluh.component.Note
import com.example.tugaskesepuluh.data.NoteRepository
import com.example.tugaskesepuluh.settings.SettingsRepository
import com.example.tugaskesepuluh.settings.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

val sampleNotes = listOf(
    Note(id = 1, title = "Belajar Compose", content = "Multiplatform navigation"),
    Note(id = 2, title = "Tugas Kuliah", content = "Deadline minggu ini", isFavorite = true),
    Note(id = 3, title = "Meeting Proyek", content = "Jam 14:00 dengan tim")
)

class FakeNoteRepository(initialNotes: List<Note> = sampleNotes) : NoteRepository {
    private val nextId = AtomicInteger((initialNotes.maxOfOrNull { it.id } ?: 0) + 1)
    private val notesState = MutableStateFlow(initialNotes)

    override val notes: Flow<List<Note>> = notesState.asStateFlow()

    override fun searchNotes(query: String): Flow<List<Note>> {
        val normalized = query.trim().lowercase()
        return notesState.map { current ->
            current.filter { note ->
                normalized.isBlank() ||
                    note.title.lowercase().contains(normalized) ||
                    note.content.lowercase().contains(normalized)
            }
        }
    }

    override suspend fun addNote(title: String, content: String) {
        val current = notesState.value.toMutableList()
        current.add(
            Note(
                id = nextId.getAndIncrement(),
                title = title,
                content = content,
                isFavorite = false
            )
        )
        notesState.value = current
    }

    override suspend fun updateNote(id: Int, title: String, content: String, isFavorite: Boolean) {
        notesState.value = notesState.value.map { note ->
            if (note.id == id) note.copy(title = title, content = content, isFavorite = isFavorite) else note
        }
    }

    override suspend fun deleteNote(id: Int) {
        notesState.value = notesState.value.filterNot { it.id == id }
    }

    override suspend fun toggleFavorite(id: Int) {
        notesState.value = notesState.value.map { note ->
            if (note.id == id) note.copy(isFavorite = !note.isFavorite) else note
        }
    }

    override suspend fun getNoteById(id: Int): Note? = notesState.value.firstOrNull { it.id == id }

    fun currentNotes(): List<Note> = notesState.value
    fun emit(notes: List<Note>) { notesState.value = notes }
}

class FakeSettingsRepository(
    initialSettings: UserSettings = UserSettings()
) : SettingsRepository {
    private val settingsState = MutableStateFlow(initialSettings)

    override val settingsFlow: Flow<UserSettings> = settingsState.asStateFlow()

    override suspend fun setThemeMode(themeMode: com.example.tugaskesepuluh.settings.ThemeMode) {
        settingsState.value = settingsState.value.copy(themeMode = themeMode)
    }

    override suspend fun setSortOrder(sortOrder: com.example.tugaskesepuluh.settings.SortOrder) {
        settingsState.value = settingsState.value.copy(sortOrder = sortOrder)
    }
}
