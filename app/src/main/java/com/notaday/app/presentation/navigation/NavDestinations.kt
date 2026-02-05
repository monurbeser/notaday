package com.notaday.app.presentation.navigation

sealed class NavDestinations(val route: String) {
    object Calendar : NavDestinations("calendar")
    object NoteList : NavDestinations("note_list")
    object NoteDetail : NavDestinations("note_detail/{noteId}") {
        fun createRoute(noteId: Long = 0) = "note_detail/$noteId"
    }
    object Settings : NavDestinations("settings")
}
