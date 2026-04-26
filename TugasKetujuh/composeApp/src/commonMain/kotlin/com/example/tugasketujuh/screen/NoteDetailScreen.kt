package com.example.tugasketujuh.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tugasketujuh.component.NoteViewModel

@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NoteViewModel,
    onEditClick: () -> Unit,
    onBack: () -> Unit
) {
    val note = viewModel.notes.collectAsState().value.firstOrNull { it.id == noteId } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            Spacer(Modifier.width(8.dp))
            Text("Back")
        }

        Spacer(Modifier.height(24.dp))

        Text(note.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(note.content, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.toggleFavorite(noteId) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (note.isFavorite) Color(0xFFFF4081) else Color.Gray
                )
            ) {
                Icon(
                    imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
                Spacer(Modifier.width(8.dp))
                Text("Favorite")
            }

            Button(onClick = onEditClick, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
                Spacer(Modifier.width(8.dp))
                Text("Edit")
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                viewModel.deleteNote(noteId)
                onBack()
            }
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Hapus")
            Spacer(Modifier.width(8.dp))
            Text("Delete Note")
        }
    }
}
