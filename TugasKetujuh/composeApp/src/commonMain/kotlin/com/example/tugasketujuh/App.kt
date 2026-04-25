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
fun App(viewModel: NoteViewModel) {
    NotesScreen(viewModel)
}