package com.example.tugasketujuh.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.db.Note
import kotlinx.coroutines.launch

@Composable
fun EditNoteScreen(
    noteId: Long,
    viewModel: NoteViewModel,
    onSave: () -> Unit
) {

    var note by remember { mutableStateOf<Note?>(null) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // 🔥 ambil data dari database
    LaunchedEffect(noteId) {
        val result = viewModel.getNoteById(noteId)
        note = result
        result?.let {
            title = it.title
            content = it.content
        }
    }

    // ⏳ loading state
    if (note == null) {
        Text("Loading...")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul") }
        )

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi catatan") }
        )

        Button(onClick = {
            scope.launch {
                viewModel.updateNote(noteId, title, content)
                onSave()
            }
        }) {
            Text("Simpan Perubahan")
        }
    }
}