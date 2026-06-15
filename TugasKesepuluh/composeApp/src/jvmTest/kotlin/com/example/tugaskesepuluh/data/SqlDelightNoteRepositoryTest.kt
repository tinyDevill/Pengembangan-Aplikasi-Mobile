package com.example.tugaskesepuluh.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.tugaskesepuluh.database.NotesDatabase
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import java.util.Properties

class SqlDelightNoteRepositoryTest {

    private lateinit var driver: JdbcSqliteDriver
    private lateinit var repository: SqlDelightNoteRepository

    @BeforeTest
    fun setUp() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, Properties(), NotesDatabase.Schema)
        repository = SqlDelightNoteRepository(driver)
    }

    @AfterTest
    fun tearDown() {
        driver.close()
    }

    @Test
    fun notesFlow_startsWithSeededNotes() = runTest {
        val notes = repository.notes.first()

        assertEquals(3, notes.size)
        assertEquals(
            listOf("Meeting Proyek", "Tugas Kuliah", "Belajar Compose"),
            notes.map { it.title }
        )
    }

    @Test
    fun addNote_persistsNoteToDatabase() = runTest {
        repository.addNote("Catatan Baru", "Isi catatan baru")

        val added = repository.searchNotes("Catatan Baru").first()

        assertEquals(1, added.size)
        assertEquals("Catatan Baru", added.first().title)
        assertEquals("Isi catatan baru", added.first().content)
        assertFalse(added.first().isFavorite)
    }

    @Test
    fun updateNote_changesExistingRow() = runTest {
        repository.updateNote(
            id = 2,
            title = "Tugas Kuliah Diperbarui",
            content = "Deadline minggu depan",
            isFavorite = false
        )

        val updated = repository.getNoteById(2)

        assertEquals("Tugas Kuliah Diperbarui", updated?.title)
        assertEquals("Deadline minggu depan", updated?.content)
        assertEquals(false, updated?.isFavorite)
    }

    @Test
    fun toggleFavorite_flipsFavoriteFlag() = runTest {
        val before = repository.getNoteById(1)
        assertEquals(false, before?.isFavorite)

        repository.toggleFavorite(1)

        val after = repository.getNoteById(1)
        assertTrue(after?.isFavorite == true)
    }

    @Test
    fun deleteNote_removesRowFromDatabase() = runTest {
        repository.deleteNote(3)

        val deleted = repository.getNoteById(3)

        assertNull(deleted)
        assertEquals(2, repository.notes.first().size)
    }

    @Test
    fun searchNotes_returnsMatchingNotes() = runTest {
        val result = repository.searchNotes("Deadline").first()

        assertEquals(1, result.size)
        assertEquals("Tugas Kuliah", result.first().title)
    }
}
