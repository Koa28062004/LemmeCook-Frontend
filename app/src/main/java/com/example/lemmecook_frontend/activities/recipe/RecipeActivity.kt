// ComposeActivity.kt
package com.example.lemmecook_frontend.activities.recipe

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lemmecook_frontend.activities.NavHost.AppDestination
import com.example.lemmecook_frontend.activities.NavHost.AppTabRowScreens
import com.example.lemmecook_frontend.activities.NavHost.RecipeNavHost
import com.example.lemmecook_frontend.activities.NavHost.RecipeOverviewScreen
import com.example.lemmecook_frontend.activities.NavHost.navigateSingleTopTo
import com.example.lemmecook_frontend.activities.NavHost.shouldShowBottomBar
import com.example.lemmecook_frontend.fragments.AppTabRow
import com.example.lemmecook_frontend.ui.theme.LemmeCookFrontendTheme

class RecipeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemmeCookFrontendTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                var previousScreen by remember { mutableStateOf<AppDestination>(RecipeOverviewScreen) }

                LaunchedEffect(currentDestination?.route) {
                    val newScreen = AppTabRowScreens.find { it.route == currentDestination?.route }
                    newScreen?.let {
                        previousScreen = it
                    }
                }

                val currentScreen = AppTabRowScreens.find { it.route == currentDestination?.route }
                    ?: previousScreen

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar(currentDestination?.route.toString())) {
                            AppTabRow(
                                allScreens = AppTabRowScreens,
                                onTabSelected = { newScreen ->
                                    navController.navigateSingleTopTo(
                                        newScreen.route
                                    )
                                },
                                currentScreen = currentScreen
                            )
                        }
                    }
                ) { innerPadding ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        RecipeNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
