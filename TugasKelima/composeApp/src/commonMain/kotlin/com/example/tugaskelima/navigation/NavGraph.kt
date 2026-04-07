package com.example.tugaskelima.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.*

import com.example.tugaskelima.navigation.Routes
import com.example.tugaskelima.screens.notes.*
import com.example.tugaskelima.screens.favorites.FavoritesScreen
import com.example.tugaskelima.screens.profile.ProfileScreen
import com.example.tugaskelima.components.BottomBar
import com.example.tugaskelima.viewmodel.NoteViewModel

@Composable
fun NavGraph(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.AddNote.route)
            }) {
                Text("+")
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Notes.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Routes.Notes.route) {
                NotesScreen(navController, viewModel)
            }

            composable(Routes.NoteDetail.route) { backStack ->
                val noteId = backStack.destination.route
                    ?.substringAfter("noteDetail/")
                    ?.toIntOrNull() ?: 0

                NoteDetailScreen(navController, viewModel, noteId)
            }

            composable(Routes.AddNote.route) {
                AddEditNoteScreen(navController, viewModel)
            }

            composable(Routes.EditNote.route) { backStack ->
                val noteId = backStack.destination.route
                    ?.substringAfter("editNote/")
                    ?.toIntOrNull() ?: 0

                AddEditNoteScreen(navController, viewModel, noteId)
            }

            composable(Routes.Favorites.route) {
                FavoritesScreen(viewModel)
            }

            composable(Routes.Profile.route) {
                ProfileScreen()
            }
        }
    }
}