package com.example.tugaskeempat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform