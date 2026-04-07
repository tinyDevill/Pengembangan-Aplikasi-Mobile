package com.example.tugaskelima.screens.notes

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.*

import com.example.tugaskelima.viewmodel.NoteViewModel


@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    noteId: Int? = null
) {

    val existingNote = noteId?.let { viewModel.getNoteById(it) }

    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var content by remember { mutableStateOf(existingNote?.content ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(if (noteId == null) "Add Note" else "Edit Note")

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") }
        )

        Button(onClick = {
            if (noteId == null) {
                viewModel.addNote(title, content)
            } else {
                viewModel.updateNote(noteId, title, content)
            }

            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}