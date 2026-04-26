package com.example.tugasketujuh.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tugasketujuh.component.NoteItem
import com.example.tugasketujuh.component.NoteViewModel

@Composable
fun FavoritesScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Int) -> Unit
) {
    val favorites = viewModel.notes.collectAsState().value.filter { it.isFavorite }

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "There is no favorite note yet",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(favorites, key = { it.id }) { note ->
            NoteItem(
                note = note,
                onClick = { onNoteClick(note.id) }
            )
        }
    }
}
