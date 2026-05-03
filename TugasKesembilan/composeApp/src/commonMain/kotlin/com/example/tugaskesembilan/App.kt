package com.example.tugaskesembilan

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugaskesembilan.data.ai.AIRepositoryImpl
import com.example.tugaskesembilan.data.ai.GeminiService
import com.example.tugaskesembilan.network.createHttpClient
import com.example.tugaskesembilan.presentation.summarizer.SummarizerScreen
import com.example.tugaskesembilan.presentation.summarizer.SummarizerViewModel

@Composable
@Preview
fun App() {
    val client = remember { createHttpClient() }
    DisposableEffect(Unit) {
        onDispose { client.close() }
    }

    val repository = remember(client) {
        AIRepositoryImpl(GeminiService(client))
    }
    val viewModel = remember(repository) {
        SummarizerViewModel(repository)
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SummarizerScreen(viewModel = viewModel)
        }
    }
}
