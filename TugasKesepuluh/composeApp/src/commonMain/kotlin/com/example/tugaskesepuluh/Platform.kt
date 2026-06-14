package com.example.tugaskesepuluh

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform