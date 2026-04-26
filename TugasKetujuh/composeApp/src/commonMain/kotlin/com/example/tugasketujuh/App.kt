package com.example.tugasketujuh

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasketujuh.component.NoteViewModel
import com.example.tugasketujuh.settings.DataStoreSettingsRepository
import com.example.tugasketujuh.data.SqlDelightNoteRepository
import com.example.tugasketujuh.database.AppDependencies
import com.example.tugasketujuh.navigation.Screen
import com.example.tugasketujuh.screen.AddNoteScreen
import com.example.tugasketujuh.screen.BottomNavigationBar
import com.example.tugasketujuh.screen.EditNoteScreen
import com.example.tugasketujuh.screen.FavoritesScreen
import com.example.tugasketujuh.screen.NoteDetailScreen
import com.example.tugasketujuh.screen.NotesScreen
import com.example.tugasketujuh.screen.ProfileScreen
import com.example.tugasketujuh.screen.SettingsScreen
import com.example.tugasketujuh.settings.AppTheme
import com.example.tugasketujuh.settings.SettingsViewModel
import com.example.tugasketujuh.settings.createSettingsDataStore

@Composable
fun App() {
    val navController = rememberNavController()
    val repository = remember {
        SqlDelightNoteRepository(
            AppDependencies.requireDatabaseDriverFactory().createDriver()
        )
    }
    val settingsRepository = remember {
        DataStoreSettingsRepository(createSettingsDataStore())
    }
    val settingsViewModel = remember { SettingsViewModel(settingsRepository) }
    val themeSettings = settingsViewModel.settings.collectAsState().value
    val viewModel = remember {
        NoteViewModel(
            repository = repository,
            settingsRepository = settingsRepository
        )
    }

    DisposableEffect(viewModel, settingsViewModel) {
        onDispose {
            viewModel.clear()
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
                        viewModel = viewModel,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                    )
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        viewModel = viewModel,
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
                        viewModel = viewModel,
                        onEditClick = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Screen.AddNote.route) {
                    AddNoteScreen(
                        viewModel = viewModel,
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
                        viewModel = viewModel,
                        onSave = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
