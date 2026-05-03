package com.example.tugaskesembilan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform