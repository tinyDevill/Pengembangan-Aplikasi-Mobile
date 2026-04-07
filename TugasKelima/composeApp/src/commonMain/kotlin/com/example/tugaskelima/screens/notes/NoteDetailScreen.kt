package com.example.tugaskelima.screens.notes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController

import com.example.tugaskelima.navigation.Routes
import com.example.tugaskelima.viewmodel.NoteViewModel

@Composable
fun NoteDetailScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    noteId: Int
) {

    val note = viewModel.getNoteById(noteId)

    Column(modifier = Modifier.padding(16.dp)) {

        Text(note?.title ?: "No Title")
        Text(note?.content ?: "No Content")

        Button(onClick = {
            navController.navigate(
                Routes.EditNote.createRoute(noteId)
            )
        }) {
            Text("Edit")
        }

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }
    }
}