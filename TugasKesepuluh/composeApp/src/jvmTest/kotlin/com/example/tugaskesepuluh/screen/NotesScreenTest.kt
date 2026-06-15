package com.example.tugaskesepuluh.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.example.tugaskesepuluh.FakeNoteRepository
import com.example.tugaskesepuluh.FakeSettingsRepository
import com.example.tugaskesepuluh.component.NoteViewModel
import com.example.tugaskesepuluh.sampleNotes
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers

class NotesScreenTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun showsEmptyStateWhenNoNotesExist() = runComposeUiTest {
        val viewModel = NoteViewModel(
            repository = FakeNoteRepository(emptyList()),
            settingsRepository = FakeSettingsRepository(),
            dispatcher = Dispatchers.Unconfined
        )

        setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                showNetworkStatus = false
            )
        }

        waitForIdle()

        onNodeWithText("There are no notes yet").assertIsDisplayed()

        viewModel.clear()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun showsLoadedNotes() = runComposeUiTest {
        val viewModel = NoteViewModel(
            repository = FakeNoteRepository(sampleNotes),
            settingsRepository = FakeSettingsRepository(),
            dispatcher = Dispatchers.Unconfined
        )

        setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                showNetworkStatus = false
            )
        }

        waitForIdle()

        onNodeWithText("Belajar Compose").assertIsDisplayed()
        onNodeWithText("Tugas Kuliah").assertIsDisplayed()

        viewModel.clear()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun searchFiltersNotesAndClearButtonRestoresList() = runComposeUiTest {
        val viewModel = NoteViewModel(
            repository = FakeNoteRepository(sampleNotes),
            settingsRepository = FakeSettingsRepository(),
            dispatcher = Dispatchers.Unconfined
        )

        setContent {
            NotesScreen(
                viewModel = viewModel,
                onNoteClick = {},
                showNetworkStatus = false
            )
        }

        waitForIdle()

        onNodeWithTag("notes_search_field").performTextInput("Belajar")
        waitForIdle()

        onNodeWithText("Belajar Compose").assertIsDisplayed()
        onNodeWithText("Tugas Kuliah").assertDoesNotExist()

        onNodeWithTag("notes_clear_search").assertIsDisplayed().performClick()
        waitForIdle()

        onNodeWithText("Belajar Compose").assertIsDisplayed()
        onNodeWithText("Tugas Kuliah").assertIsDisplayed()

        viewModel.clear()
    }
}
