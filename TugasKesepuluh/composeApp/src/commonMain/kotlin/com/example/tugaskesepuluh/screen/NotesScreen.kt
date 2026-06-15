package com.example.tugaskesepuluh.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tugaskesepuluh.component.NoteItem
import com.example.tugaskesepuluh.component.NoteViewModel
import com.example.tugaskesepuluh.component.NotesUiState

@Composable
fun NotesScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Int) -> Unit,
    showNetworkStatus: Boolean = true
) {
    val searchQuery = viewModel.query.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showNetworkStatus) {
            NetworkStatusIndicator()
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::setSearchQuery,
            modifier = Modifier.fillMaxWidth().testTag("notes_search_field"),
            singleLine = true,
            label = { Text("Search Notes") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(
                        onClick = viewModel::clearSearch,
                        modifier = Modifier.testTag("notes_clear_search")
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Search History")
                    }
                }
            }
        )

        when (uiState) {
            NotesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().testTag("notes_loading"),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(36.dp))
                }
            }

            is NotesUiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize().testTag("notes_empty"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is NotesUiState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().testTag("notes_list"),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.notes, key = { it.id }) { note ->
                        NoteItem(
                            note = note,
                            onClick = { onNoteClick(note.id) }
                        )
                    }
                }
            }
        }
    }
}
