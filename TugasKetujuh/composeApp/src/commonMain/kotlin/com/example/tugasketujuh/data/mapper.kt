package com.example.tugasketujuh.data

import com.example.tugasketujuh.Note
import com.example.tugasketujuh.database.NotesEntity

/**
 * Mapper dari database (SQLDelight) → domain model
 */
fun NotesEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        isFavorite = isFavorite == 1L
    )
}

/**
 * Mapper dari domain model → database (untuk insert/update)
 */
fun Note.toEntity(): NotesEntity {
    return NotesEntity(
        id = id,
        title = title,
        content = content,
        isFavorite = if (isFavorite) 1L else 0L
    )
}