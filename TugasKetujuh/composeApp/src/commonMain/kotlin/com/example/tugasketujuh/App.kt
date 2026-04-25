// App.kt
package com.example.tugasketujuh

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.navigation.Screen
import com.example.tugasketujuh.screen.*

@Composable
fun App(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Notes Screen
            composable(Screen.Notes.route) {
                NotesScreen(viewModel = viewModel, navController = navController)
            }

            // Favorites Screen
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            // Profile Screen
            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            // Add Note Screen
            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    viewModel = viewModel,
                    onSave = { navController.popBackStack() }
                )
            }

            // Note Detail Screen
            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(
                    navArgument("noteId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                NoteDetailScreen(
                    noteId = noteId,
                    viewModel = viewModel,
                    onEditClick = {
                        navController.navigate(Screen.EditNote.createRoute(noteId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // Edit Note Screen
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                EditNoteScreen(
                    noteId = noteId.toLong(),
                    viewModel = viewModel,
                    onSave = { navController.popBackStack() }
                )
            }
        }
    }
}