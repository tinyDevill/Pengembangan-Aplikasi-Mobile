package com.example.tugaskelima.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskelima.component.NoteItem
import com.example.tugaskelima.component.NoteViewModel

@Composable
fun FavoritesScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Int) -> Unit          // ← Tambahan ini
) {
    val favorites = viewModel.notes.filter { it.isFavorite }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(favorites) { note ->
            NoteItem(
                note = note,
                onClick = { onNoteClick(note.id) }   // ← sekarang bisa klik
            )
        }
    }
}