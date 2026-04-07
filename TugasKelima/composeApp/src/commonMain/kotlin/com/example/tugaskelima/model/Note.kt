package com.example.tugaskelima.model

data class Note(
    val id: Int,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false
)