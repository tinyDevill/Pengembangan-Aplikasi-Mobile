package com.example.tugaskesembilan.data.ai

interface AIRepository {
    suspend fun summarize(text: String): Result<String>
}
