package com.example.tugasketiga

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.draw.clip

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import tugasketiga.composeapp.generated.resources.LavaLamp
import tugasketiga.composeapp.generated.resources.Res
import tugasketiga.composeapp.generated.resources.compose_multiplatform
import tugasketiga.composeapp.generated.resources.me

@Composable
fun App() {
    MaterialTheme {
        ProfileScreen()
    }
}

@Composable
fun ProfileScreen() {
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
                name = "Abi Sholihan",
                bio = "Informatics Engineering Student | ITERA"
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProfileCard {
                Column {

                    InfoItem("Email", "abi.123140192@student.itera.ac.id")
                    InfoItem("Phone", "+62 857 8305 7625")
                    InfoItem("Location", "Lampung, Indonesia")

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }) {
                Text("Follow")
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
