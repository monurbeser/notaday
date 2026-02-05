package com.notaday.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.navigation.compose.rememberNavController
import com.notaday.app.presentation.navigation.NavGraph
import com.notaday.app.presentation.theme.NotadayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotadayTheme(darkTheme = isSystemInDarkTheme()) {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}