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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val note = viewModel.getNoteById(noteId) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tombol Kembali
        Button(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
            Spacer(Modifier.width(8.dp))
            Text("Kembali")
        }

        Spacer(Modifier.height(24.dp))

        // Judul & Isi
        Text(note.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(note.content, style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(32.dp))

        // Tombol Favorit + Edit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tombol Favorit (Baru!)
            Button(
                onClick = { viewModel.toggleFavorite(noteId) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (note.isFavorite) Color(0xFFFF4081) else Color.Gray
                )
            ) {
                Icon(
                    imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorit"
                )
                Spacer(Modifier.width(8.dp))
                Text(if (note.isFavorite) "Hapus dari Favorit" else "Jadikan Favorit")
            }

            // Tombol Edit
            Button(onClick = onEditClick, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
                Spacer(Modifier.width(8.dp))
                Text("Edit Catatan")
            }
        }
    }
}