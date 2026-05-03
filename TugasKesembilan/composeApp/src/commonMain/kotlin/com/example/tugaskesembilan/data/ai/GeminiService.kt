package com.example.tugaskesembilan.data.ai

import com.example.tugaskesembilan.config.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import io.ktor.client.request.*
import java.io.IOException

sealed class AIError(message: String) : Exception(message) {
    class MissingApiKey : AIError("GEMINI_API_KEY belum diisi.")
    class Unauthorized : AIError("API key tidak valid atau tidak punya akses.")
    class RateLimited(val retryAfterSeconds: Int) : AIError("Terlalu banyak request. Coba lagi nanti.")
    class ServerError : AIError("Server Gemini sedang bermasalah.")
    class NetworkError : AIError("Koneksi internet bermasalah.")
    class ParseError : AIError("Response AI tidak bisa diproses.")
    class InvalidPrompt : AIError("Prompt kosong atau tidak valid.")
}

class GeminiService(private val client: HttpClient) {

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.0-flash"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun summarize(text: String): Result<String> = runCatching {
        val cleanedText = text.trim()
        if (cleanedText.isBlank()) throw AIError.InvalidPrompt()

        val apiKey = ApiConfig.geminiApiKey
        if (apiKey.isBlank()) throw AIError.MissingApiKey()

        val prompt = buildSummaryPrompt(cleanedText)
        val requestBody = buildGeminiRequest(prompt)

        val responseText: String = client.post("$baseUrl/models/$model:generateContent") {
            contentType(ContentType.Application.Json)
            url {
                parameters.append("key", apiKey)
            }
            setBody(TextContent(requestBody, ContentType.Application.Json))
        }.body()

        parseSummary(responseText)
    }.recoverCatching { error ->
        when (error) {
            is ClientRequestException -> when (error.response.status.value) {
                401 -> throw AIError.Unauthorized()
                429 -> {
                    val retryAfter = error.response.headers["Retry-After"]?.toIntOrNull() ?: 60
                    throw AIError.RateLimited(retryAfter)
                }
                in 500..599 -> throw AIError.ServerError()
                else -> throw error
            }
            is ServerResponseException -> throw AIError.ServerError()
            is IOException -> throw AIError.NetworkError()
            is AIError -> throw error
            else -> throw AIError.ParseError()
        }
    }

    private fun buildSummaryPrompt(text: String): String = """
        Kamu adalah asisten ringkas yang sangat teliti.
        Tugas: ringkas teks berikut menjadi 2-3 kalimat dalam Bahasa Indonesia.
        Aturan:
        - Jawab hanya isi ringkasan, tanpa pembuka atau penutup.
        - Pertahankan makna utama.
        - Jika teks terlalu panjang, prioritaskan ide paling penting.

        Teks:
        $text
    """.trimIndent()

    private fun buildGeminiRequest(prompt: String): String {
        val request = buildJsonObject {
            put("contents", buildJsonArray {
                add(buildJsonObject {
                    put("role", JsonPrimitive("user"))
                    put("parts", buildJsonArray {
                        add(buildJsonObject {
                            put("text", JsonPrimitive(prompt))
                        })
                    })
                })
            })
            put("generationConfig", buildJsonObject {
                put("temperature", JsonPrimitive(0.2))
                put("maxOutputTokens", JsonPrimitive(256))
                put("topP", JsonPrimitive(0.95))
            })
        }
        return request.toString()
    }

    private fun parseSummary(responseText: String): String {
        val root = json.parseToJsonElement(responseText).jsonObject
        val candidates = root["candidates"]?.jsonArray ?: throw AIError.ParseError()
        val firstCandidate = candidates.firstOrNull()?.jsonObject ?: throw AIError.ParseError()
        val content = firstCandidate["content"]?.jsonObject ?: throw AIError.ParseError()
        val parts = content["parts"]?.jsonArray ?: throw AIError.ParseError()
        val firstPart = parts.firstOrNull()?.jsonObject ?: throw AIError.ParseError()
        val summary = firstPart["text"]?.jsonPrimitive?.content?.trim().orEmpty()

        if (summary.isBlank()) throw AIError.ParseError()
        return summary
    }
}
