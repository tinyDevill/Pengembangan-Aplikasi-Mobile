package com.example.tugaskelima.component

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)