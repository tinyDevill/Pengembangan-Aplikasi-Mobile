package com.example.tugaskeenam.data.repository

import com.example.tugaskeenam.data.model.Post
import com.example.tugaskeenam.data.remote.HttpClientFactory
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsRepository {
    private val client = HttpClientFactory.create()
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getPosts(): Result<List<Post>> {
        return try {
            val posts: List<Post> = client.get("$baseUrl/posts").body()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}