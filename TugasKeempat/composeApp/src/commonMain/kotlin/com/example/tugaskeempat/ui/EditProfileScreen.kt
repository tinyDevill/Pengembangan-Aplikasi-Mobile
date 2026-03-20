package com.example.tugaskeempat.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskeempat.viewmodel.ProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf(state.name) }
    var bio by remember { mutableStateOf(state.bio) }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Edit Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") }
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.updateProfile(name, bio)
            onBack()
        }) {
            Text("Save")
        }
    }
}