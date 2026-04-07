package com.example.tugaskelima.screens.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

import com.example.tugaskelima.viewmodel.NoteViewModel

@Composable
fun FavoritesScreen(viewModel: NoteViewModel) {

    val favorites = viewModel.notes.filter { it.isFavorite }

    LazyColumn {
        items(favorites.size) { index ->
            val note = favorites[index]
            Text(note.title)
        }
    }
}