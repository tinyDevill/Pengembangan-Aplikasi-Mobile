package com.example.tugaskeempat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import com.example.tugaskeempat.viewmodel.ProfileViewModel
import com.example.tugaskeempat.ui.EditProfileScreen
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import tugaskeempat.composeapp.generated.resources.Res
import tugaskeempat.composeapp.generated.resources.me

@Composable
fun App() {

    val viewModel = remember { ProfileViewModel() }
    val state by viewModel.uiState.collectAsState()

    var isEditScreen by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = if (state.isDarkMode)
            darkColorScheme()
        else
            lightColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            if (isEditScreen) {
                EditProfileScreen(
                    viewModel = viewModel,
                    onBack = { isEditScreen = false }
                )
            } else {
                ProfileScreen(
                    viewModel = viewModel,
                    onEditClick = { isEditScreen = true }
                )
            }

        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onEditClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProfileHeader(
                name = state.name,
                bio = state.bio
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileCard {
                Column {

                    InfoItem("Email", state.email)
                    InfoItem("Phone", state.phone)
                    InfoItem("Location", state.location)

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Mode")

                Spacer(modifier = Modifier.width(10.dp))

                Switch(
                    checked = state.isDarkMode,
                    onCheckedChange = {
                        viewModel.toggleDarkMode()
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { onEditClick() }) {
                Text("Edit Profile")
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    bio: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(Res.drawable.me),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = bio,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProfileCard(
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }

    }
}

@Composable
fun InfoItem(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = value
        )

    }
}
