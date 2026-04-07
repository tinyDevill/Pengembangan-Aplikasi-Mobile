package com.example.tugaskelima

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform