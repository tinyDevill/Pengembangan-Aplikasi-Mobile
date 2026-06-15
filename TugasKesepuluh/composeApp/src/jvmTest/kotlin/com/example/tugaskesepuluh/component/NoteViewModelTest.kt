@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.tugaskesepuluh.component

import com.example.tugaskesepuluh.sampleNotes
import com.example.tugaskesepuluh.data.NoteRepository
import com.example.tugaskesepuluh.settings.SettingsRepository
import com.example.tugaskesepuluh.settings.UserSettings
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

class NoteViewModelTest {

    private fun createViewModel(
        dispatcher: kotlinx.coroutines.CoroutineDispatcher,
        repository: NoteRepository,
        settingsRepository: SettingsRepository,
        notes: List<Note> = sampleNotes,
        settings: UserSettings = UserSettings()
    ): NoteViewModel {
        every { repository.notes } returns flowOf(notes)
        every { repository.searchNotes(any()) } returns flowOf(emptyList())
        every { settingsRepository.settingsFlow } returns flowOf(settings)
        return NoteViewModel(repository, settingsRepository, dispatcher)
    }

    @Test
    fun setSearchQuery_updatesQueryState() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.setSearchQuery("compose")

        assertEquals("compose", viewModel.query.value)
        viewModel.clear()
    }

    @Test
    fun clearSearch_resetsQueryState() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.setSearchQuery("deadline")
        viewModel.clearSearch()

        assertEquals("", viewModel.query.value)
        viewModel.clear()
    }

    @Test
    fun addNote_delegatesToRepository() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        coEvery { repository.addNote("Judul Baru", "Isi baru") } returns Unit

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.addNote("Judul Baru", "Isi baru")
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.addNote("Judul Baru", "Isi baru") }
        viewModel.clear()
    }

    @Test
    fun updateNote_delegatesToRepository() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        coEvery { repository.updateNote(any(), any(), any(), any()) } returns Unit

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.updateNote(
            id = 2,
            title = "Judul Diperbarui",
            content = "Konten diperbarui",
            isFavorite = true
        )
        advanceUntilIdle()

        coVerify(exactly = 1) {
            repository.updateNote(2, "Judul Diperbarui", "Konten diperbarui", true)
        }
        viewModel.clear()
    }

    @Test
    fun deleteNote_delegatesToRepository() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        coEvery { repository.deleteNote(3) } returns Unit

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.deleteNote(3)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.deleteNote(3) }
        viewModel.clear()
    }

    @Test
    fun toggleFavorite_delegatesToRepository() = runTest {
        val repository = mockk<NoteRepository>()
        val settingsRepository = mockk<SettingsRepository>()

        coEvery { repository.toggleFavorite(1) } returns Unit

        val viewModel = createViewModel(
            dispatcher = UnconfinedTestDispatcher(testScheduler),
            repository = repository,
            settingsRepository = settingsRepository
        )

        viewModel.toggleFavorite(1)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.toggleFavorite(1) }
        viewModel.clear()
    }
}
