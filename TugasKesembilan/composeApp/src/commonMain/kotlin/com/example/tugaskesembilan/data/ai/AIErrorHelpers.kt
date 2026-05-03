package com.example.tugaskesembilan.data.ai

suspend fun <T> safeAICall(block: suspend () -> T): Result<T> = runCatching {
    block()
}
