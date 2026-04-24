package com.example.tugasketujuh

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform