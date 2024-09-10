package com.example.lemmecook_frontend.activities.NavHost

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lemmecook_frontend.fragments.RecipeCongratsScreen
import com.example.lemmecook_frontend.fragments.RecipeOverviewScreen
import com.example.lemmecook_frontend.fragments.RecipePrepScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = RecipeOverviewScreen.route,
        modifier = modifier
    ) {
        // Recipe Details
        composable(route = RecipeOverviewScreen.route) {
            RecipeOverviewScreen(navController)
        }
        composable(route = RecipePrepScreen.route) {
            RecipePrepScreen(navController)
        }
        composable(route = RecipeCongratsScreen.route) {
            RecipeCongratsScreen(navController)
        }
    }
}