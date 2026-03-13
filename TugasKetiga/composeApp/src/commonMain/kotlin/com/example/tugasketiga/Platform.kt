package com.example.tugasketiga

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform