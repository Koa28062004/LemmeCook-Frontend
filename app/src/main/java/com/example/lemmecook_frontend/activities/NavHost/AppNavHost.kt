package com.example.lemmecook_frontend.activities.NavHost

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lemmecook_frontend.fragments.LandingScreenPreview
import com.example.lemmecook_frontend.fragments.SignInScreenPreview

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LandingScreen.route,
        modifier = modifier
    ) {
        composable(route = LandingScreen.route) {
            LandingScreenPreview(navController)
        }
        composable(route = SignInScreen.route) {
            SignInScreenPreview(navController)
        }
    }
}

fun NavHostController.navigateTo(route: String) =
    this.navigate(route) {

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

val bottomBarTabs = BottomBarTab.entries.toTypedArray()
private val bottomBarRoutes = bottomBarTabs.map { it.route }

enum class BottomBarTab(
    val route: String
) {
    HOME(
        Home.route
    ),
    BOOKING(
        Booking.route
    ),
    NOTIFICATION(
        Notification.route
    ),
    ACCOUNT(
        Account.route
    )
}

fun shouldShowBottomBar(route: String) = route in bottomBarRoutes