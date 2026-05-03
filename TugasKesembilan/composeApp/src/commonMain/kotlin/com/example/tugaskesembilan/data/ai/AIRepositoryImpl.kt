package com.example.tugaskesembilan.data.ai

class AIRepositoryImpl(
    private val geminiService: GeminiService
) : AIRepository {
    override suspend fun summarize(text: String): Result<String> {
        return geminiService.summarize(text)
    }
}
