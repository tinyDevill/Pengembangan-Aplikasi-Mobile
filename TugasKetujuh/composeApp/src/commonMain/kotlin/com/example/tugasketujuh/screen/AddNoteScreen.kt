package com.example.tugasketujuh.screen

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
import com.example.tugasketujuh.component.NoteViewModel

@Composable
fun AddNoteScreen(viewModel: NoteViewModel, onSave: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Judul") })
        TextField(value = content, onValueChange = { content = it }, label = { Text("Isi catatan") })
        Button(onClick = {
            if (title.isNotBlank()) {
                viewModel.addNote(title, content)
                onSave()
            }
        }) { Text("Simpan Catatan Baru") }
    }
}