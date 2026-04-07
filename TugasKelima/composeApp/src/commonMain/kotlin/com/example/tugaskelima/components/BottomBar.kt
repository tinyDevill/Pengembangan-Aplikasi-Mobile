package com.example.tugaskelima.components

import androidx.compose.runtime.Composable
import androidx.compose.material3.*

import androidx.navigation.NavController

import com.example.tugaskelima.navigation.Routes

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        Routes.Notes,
        Routes.Favorites,
        Routes.Profile
    )

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate(screen.route)
                },
                icon = { Text(screen.route) }
            )
        }
    }
}