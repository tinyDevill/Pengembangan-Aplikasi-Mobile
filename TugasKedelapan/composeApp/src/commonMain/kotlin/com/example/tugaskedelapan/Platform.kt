package com.example.tugaskedelapan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform