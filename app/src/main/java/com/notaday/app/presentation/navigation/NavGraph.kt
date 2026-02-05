package com.notaday.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notaday.app.presentation.calendar.CalendarScreen
import com.notaday.app.presentation.notedetail.NoteDetailScreen
import com.notaday.app.presentation.notelist.NoteListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.Calendar.route
    ) {
        composable(NavDestinations.Calendar.route) {
            CalendarScreen(
                onNoteClick = { noteId ->
                    navController.navigate(NavDestinations.NoteDetail.createRoute(noteId))
                },
                onAddNoteClick = {
                    navController.navigate(NavDestinations.NoteDetail.createRoute(0))
                },
                onNavigateToList = {
                    navController.navigate(NavDestinations.NoteList.route)
                }
            )
        }

        composable(
            route = NavDestinations.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") { type = NavType.LongType }
            )
        ) {
            NoteDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(NavDestinations.NoteList.route) {
            NoteListScreen(
                onNoteClick = { noteId ->
                    navController.navigate(NavDestinations.NoteDetail.createRoute(noteId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
