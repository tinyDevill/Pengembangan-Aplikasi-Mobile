package com.example.tugaskelima.navigation

sealed class Routes(val route: String) {
    object Notes : Routes("notes")
    object Favorites : Routes("favorites")
    object Profile : Routes("profile")

    object NoteDetail : Routes("noteDetail/{noteId}") {
        fun createRoute(noteId: Int) = "noteDetail/$noteId"
    }

    object AddNote : Routes("addNote")

    object EditNote : Routes("editNote/{noteId}") {
        fun createRoute(noteId: Int) = "editNote/$noteId"
    }
}