package com.example.lemmecook_frontend.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.Coil
import coil.ImageLoader
import com.example.lemmecook_frontend.activities.NavHost.AppDestination
import com.example.lemmecook_frontend.activities.NavHost.AppNavHost
import com.example.lemmecook_frontend.activities.NavHost.AppTabRowScreens
import com.example.lemmecook_frontend.activities.NavHost.Blog
import com.example.lemmecook_frontend.activities.NavHost.LandingScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateSingleTopTo
import com.example.lemmecook_frontend.activities.NavHost.shouldShowBottomBar
import com.example.lemmecook_frontend.fragments.AppTabRow
import com.example.lemmecook_frontend.models.viewmodels.RecipeViewModel
import com.example.lemmecook_frontend.ui.theme.LemmeCookFrontendTheme

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        Coil.setImageLoader {
            ImageLoader.Builder(this)
                .build()
        }

        val startDestination = intent.getStringExtra("startDestination") ?: LandingScreen.route
        val recipeId = intent.getIntExtra("recipeID", -1)

        setContent {
            LemmeCookFrontendApp(startDestination, recipeId)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LemmeCookFrontendApp(startDestination: String, recipeId: Int) {
    LemmeCookFrontendTheme {
        val recipeViewModel: RecipeViewModel = viewModel()
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        var previousScreen by remember { mutableStateOf<AppDestination>(Blog) }

        if (recipeId != -1) {
            recipeViewModel.fetchRecipeFromAPI(recipeId)
        }

        LaunchedEffect(currentDestination?.route) {
            val newScreen = AppTabRowScreens.find { it.route == currentDestination?.route }
            newScreen?.let {
                previousScreen = it
            }
        }

        val currentScreen = AppTabRowScreens.find { it.route == currentDestination?.route } ?: previousScreen
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar(currentDestination?.route.toString())) {
                    AppTabRow(
                        allScreens = AppTabRowScreens,
                        onTabSelected = { newScreen -> navController.navigateSingleTopTo(newScreen.route) },
                        currentScreen = currentScreen
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                startDestination = startDestination,
                recipeId = recipeId,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun PreviewApp() {
//    LemmeCookFrontendApp(LandingScreen.route, -1)
//}
