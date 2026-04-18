package com.example.tugaskeenam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform