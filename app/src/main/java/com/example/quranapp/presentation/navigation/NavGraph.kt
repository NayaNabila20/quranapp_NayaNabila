package com.example.quranapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.quranapp.presentation.screens.search.SearchScreen // Import screen baru
import com.example.quranapp.presentation.screens.surah.SurahDetailScreen
import com.example.quranapp.presentation.screens.surah.SurahScreen

@Composable
fun QuranNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "surah_list") {
        composable("surah_list") {
            SurahScreen(
                onSurahClick = { surahNumber ->
                    navController.navigate("surah_detail/$surahNumber")
                },
                onNavigateToSearch = {
                    navController.navigate("search_screen")
                }
            )
        }

        composable(
            route = "surah_detail/{number}",
            arguments = listOf(navArgument("number") { type = NavType.IntType })
        ) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("number") ?: 1
            SurahDetailScreen(surahNumber)
        }

        // --- Tambahkan composable untuk SearchScreen ---
        composable("search_screen") {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
