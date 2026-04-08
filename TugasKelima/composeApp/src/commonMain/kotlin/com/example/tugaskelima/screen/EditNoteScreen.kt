package com.example.tugaskelima.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskelima.component.NoteViewModel

@Composable
fun EditNoteScreen(noteId: Int, viewModel: NoteViewModel, onSave: () -> Unit) {
    val note = viewModel.getNoteById(noteId) ?: return
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Judul") })
        TextField(value = content, onValueChange = { content = it }, label = { Text("Isi catatan") })
        Button(onClick = {
            viewModel.updateNote(noteId, title, content, note.isFavorite)
            onSave()
        }) { Text("Simpan Perubahan") }
    }
}