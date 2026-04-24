// App.kt
package com.example.tugasketujuh   // sesuaikan dengan package kamu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.navigation.Screen
import com.example.tugasketujuh.screen.*

@Composable
fun App() {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == Screen.Notes.route) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.AddNote.route)
                }) {
                    Icon(Icons.Default.Add, "Tambah Catatan")
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Notes.route) {
                NotesScreen(
                    viewModel = viewModel,
                    onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    onNoteClick = { id ->
                        navController.navigate(Screen.NoteDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.Profile.route) { ProfileScreen() }

            // NoteDetail (FIXED untuk KMP)
            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.StringType })
            ) { backStackEntry ->
                val noteId = (backStackEntry.arguments?.get("noteId") as? String)?.toIntOrNull() ?: 0
                NoteDetailScreen(
                    noteId = noteId,
                    viewModel = viewModel,
                    onEditClick = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddNote.route) {
                AddNoteScreen(viewModel = viewModel, onSave = { navController.popBackStack() })
            }

            // EditNote (FIXED untuk KMP)
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.StringType })
            ) { backStackEntry ->
                val noteId = (backStackEntry.arguments?.get("noteId") as? String)?.toIntOrNull() ?: 0
                EditNoteScreen(noteId = noteId, viewModel = viewModel, onSave = { navController.popBackStack() })
            }
        }
    }
}