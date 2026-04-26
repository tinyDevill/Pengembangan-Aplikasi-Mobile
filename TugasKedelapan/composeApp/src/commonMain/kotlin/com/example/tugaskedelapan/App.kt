package com.example.tugaskedelapan

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugaskedelapan.component.NoteViewModel
import com.example.tugaskedelapan.navigation.Screen
import com.example.tugaskedelapan.screen.AddNoteScreen
import com.example.tugaskedelapan.screen.BottomNavigationBar
import com.example.tugaskedelapan.screen.EditNoteScreen
import com.example.tugaskedelapan.screen.FavoritesScreen
import com.example.tugaskedelapan.screen.NoteDetailScreen
import com.example.tugaskedelapan.screen.NotesScreen
import com.example.tugaskedelapan.screen.ProfileScreen
import com.example.tugaskedelapan.screen.SettingsScreen
import com.example.tugaskedelapan.settings.AppTheme
import com.example.tugaskedelapan.settings.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = koinInject()
    val noteViewModel: NoteViewModel = koinInject()
    val themeSettings = settingsViewModel.settings.collectAsState().value

    DisposableEffect(noteViewModel, settingsViewModel) {
        onDispose {
            noteViewModel.clear()
            settingsViewModel.clear()
        }
    }

    AppTheme(themeMode = themeSettings.themeMode) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            floatingActionButton = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                if (currentRoute == Screen.Notes.route) {
                    FloatingActionButton(onClick = { navController.navigate(Screen.AddNote.route) }) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Catatan")
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
                        viewModel = noteViewModel,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                    )
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        viewModel = noteViewModel,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(onSettingsClick = { navController.navigate(Screen.Settings.route) })
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(viewModel = settingsViewModel)
                }
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
                    NoteDetailScreen(
                        noteId = noteId,
                        viewModel = noteViewModel,
                        onEditClick = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.AddNote.route) {
                    AddNoteScreen(
                        viewModel = noteViewModel,
                        onSave = { navController.popBackStack() }
                    )
                }
                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
                    EditNoteScreen(
                        noteId = noteId,
                        viewModel = noteViewModel,
                        onSave = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
