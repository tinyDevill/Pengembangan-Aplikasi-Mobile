package com.example.tugaskeempat.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaskeempat.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {

    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Text("Name: ${state.name}")
        Text("Bio: ${state.bio}")

        Spacer(Modifier.height(16.dp))

        Row {
            Text("Dark Mode")
            Switch(
                checked = state.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode() }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            // nanti pindah ke EditScreen (sementara kosong)
        }) {
            Text("Edit Profile")
        }
    }
}