package com.example.tugaskesembilan.config

actual object ApiConfig {
    actual val geminiApiKey: String = System.getenv("GEMINI_API_KEY").orEmpty()
}
