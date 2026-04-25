package com.example.tugasketujuh.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasketujuh.component.NoteViewModel   // sesuaikan package
import com.example.tugasketujuh.component.NoteItem

@Composable
fun NotesScreen(viewModel: NoteViewModel) {

    val notes by viewModel.notes.collectAsState()

    Column {
        TextField(
            value = "",
            onValueChange = { viewModel.setSearch(it) },
            label = { Text("Search") }
        )

        LazyColumn {
            items(notes) { note ->
                Text(note.title)
            }
        }
    }
}