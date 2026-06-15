@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.tugaskesepuluh.component

import app.cash.turbine.test
import com.example.tugaskesepuluh.FakeNoteRepository
import com.example.tugaskesepuluh.FakeSettingsRepository
import com.example.tugaskesepuluh.sampleNotes
import com.example.tugaskesepuluh.settings.SortOrder
import com.example.tugaskesepuluh.settings.UserSettings
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

class NoteViewModelFlowTest {

    @Test
    fun uiState_emitsContentWhenNotesExist() = runTest {
        val repository = FakeNoteRepository(sampleNotes)
        val settingsRepository = FakeSettingsRepository(
            UserSettings(sortOrder = SortOrder.TITLE_ASC)
        )
        val viewModel = NoteViewModel(
            repository = repository,
            settingsRepository = settingsRepository,
            dispatcher = StandardTestDispatcher(testScheduler)
        )

        viewModel.uiState.test {
            assertEquals(NotesUiState.Loading, awaitItem())

            advanceUntilIdle()
            val content = awaitItem() as NotesUiState.Content

            assertEquals(
                listOf("Belajar Compose", "Meeting Proyek", "Tugas Kuliah"),
                content.notes.map { it.title }
            )

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.clear()
    }

    @Test
    fun uiState_emitsEmptyNoResultForMissingSearch() = runTest {
        val repository = FakeNoteRepository(sampleNotes)
        val settingsRepository = FakeSettingsRepository()
        val viewModel = NoteViewModel(
            repository = repository,
            settingsRepository = settingsRepository,
            dispatcher = StandardTestDispatcher(testScheduler)
        )

        viewModel.uiState.test {
            assertEquals(NotesUiState.Loading, awaitItem())

            advanceUntilIdle()
            val content = awaitItem() as NotesUiState.Content
            assertEquals(3, content.notes.size)

            viewModel.setSearchQuery("tidak ada")
            advanceUntilIdle()
            assertEquals(NotesUiState.Loading, awaitItem())

            advanceUntilIdle()
            val empty = awaitItem() as NotesUiState.Empty
            assertEquals("No result", empty.message)

            cancelAndIgnoreRemainingEvents()
        }

        viewModel.clear()
    }
}
