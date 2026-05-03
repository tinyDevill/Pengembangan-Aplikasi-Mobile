package com.example.tugaskesembilan.data.model

data class SummarizerUiState(
    val inputText: String = "",
    val resultText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
