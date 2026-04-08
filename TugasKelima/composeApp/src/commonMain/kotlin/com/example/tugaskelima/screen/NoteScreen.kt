package com.example.tugaskelima.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskelima.component.NoteViewModel   // sesuaikan package
import com.example.tugaskelima.component.NoteItem

@Composable
fun NotesScreen(viewModel: NoteViewModel, onNoteClick: (Int) -> Unit) {
    val notes = viewModel.notes

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(notes) { note ->               // ← sudah benar
            NoteItem(
                note = note,
                onClick = { onNoteClick(note.id) }
            )
        }
    }
}