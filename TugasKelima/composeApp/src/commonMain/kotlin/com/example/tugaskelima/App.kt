package com.example.tugaskelima

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*

import com.example.tugaskelima.navigation.NavGraph
import com.example.tugaskelima.viewmodel.NoteViewModel

@Composable
fun App() {
    val viewModel = remember { NoteViewModel() }

    MaterialTheme {
        Surface {
            NavGraph(viewModel)
        }
    }
}