package com.example.tugasketujuh.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tugasketujuh.component.NoteItem
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.navigation.Screen

@Composable
fun FavoritesScreen(
    viewModel: NoteViewModel,
    navController: NavHostController
) {
    val allNotes by viewModel.notes.collectAsState()
    val favorites = allNotes.filter { it.is_favorite }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favorites.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada catatan favorit",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(favorites) { dbNote ->
                        NoteItem(
                            note = dbNote.toNote(),
                            onClick = {
                                navController.navigate(Screen.NoteDetail.createRoute(dbNote.id.toInt()))
                            }
                        )
                    }
                }
            }
        }
    }
}