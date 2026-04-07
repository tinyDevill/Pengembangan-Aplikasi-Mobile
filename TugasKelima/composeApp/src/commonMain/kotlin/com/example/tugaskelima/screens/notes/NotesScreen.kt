package com.example.tugaskelima.screens.notes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.tugaskelima.navigation.Routes
import com.example.tugaskelima.viewmodel.NoteViewModel

@Composable
fun NotesScreen(navController: NavController, viewModel: NoteViewModel) {

    LazyColumn {
        items(viewModel.notes) { note ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Routes.NoteDetail.createRoute(note.id)
                        )
                    }
                    .padding(16.dp)
            ) {
                Text(note.title)
                Text(note.content)
            }
        }
    }
}