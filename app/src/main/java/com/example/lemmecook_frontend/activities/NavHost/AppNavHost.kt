package com.example.lemmecook_frontend.activities.NavHost

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lemmecook_frontend.activities.blog.BlogScreen
import com.example.lemmecook_frontend.activities.schedule.ScheduleScreen
import com.example.lemmecook_frontend.fragments.ChooseNameScreenPreview
import com.example.lemmecook_frontend.activities.explore.ExploreMain
import com.example.lemmecook_frontend.activities.settings.Settings
import com.example.lemmecook_frontend.fragments.ForgetPasswordScreenPreview
import com.example.lemmecook_frontend.fragments.LandingScreenPreview
import com.example.lemmecook_frontend.fragments.OnboardScreenPreview
import com.example.lemmecook_frontend.fragments.RecipeCongratsScreen
import com.example.lemmecook_frontend.fragments.RecipeOverviewScreen
import com.example.lemmecook_frontend.fragments.RecipePrepScreen
import com.example.lemmecook_frontend.fragments.SignInScreenPreview
import com.example.lemmecook_frontend.fragments.SignUpScreenPreview
import com.example.lemmecook_frontend.fragments.Step1ScreenPreview
import com.example.lemmecook_frontend.fragments.Step2ScreenPreview
import com.example.lemmecook_frontend.models.viewmodels.RecipeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = LandingScreen.route,
    recipeId: Int = -1,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, // Correct usage
        startDestination = startDestination, // Pass the startDestination from function parameters
        modifier = modifier // Pass the modifier from function parameters
    ) {
        // Authentication & Onboarding
        composable(route = LandingScreen.route) {
            LandingScreenPreview(navController)
        }
        composable(route = SignInScreen.route) {
            SignInScreenPreview(navController)
        }
        composable(route = SignUpScreen.route) {
            SignUpScreenPreview(navController)
        }
        composable(route = OnboardScreen.route) {
            OnboardScreenPreview(navController)
        }
        composable(route = Step1Screen.route) {
            Step1ScreenPreview(navController)
        }
        composable(route = Step2Screen.route) {
            Step2ScreenPreview(navController)
        }
        composable(route = ForgetPasswordScreen.route) {
            ForgetPasswordScreenPreview(navController)
        }
        composable(
            route = "choose_name/{email}/{password}"
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val password = backStackEntry.arguments?.getString("password")
            ChooseNameScreenPreview(navController, email, password)
        }

        composable(route = Schedule.route) {
            ScheduleScreen()
        }

        // Recipe Details
        composable(route = RecipeOverviewScreen.route) {
            RecipeOverviewScreen(navController, recipeId)
        }
        composable(route = RecipePrepScreen.route) {
            RecipePrepScreen(navController)
        }
        composable(route = RecipeCongratsScreen.route) {
            RecipeCongratsScreen(navController)
        }
        composable(route = ExploreScreen.route) {
            val context = LocalContext.current
            context.startActivity(Intent(context, ExploreMain::class.java))
        }
        composable(route = Blog.route) {
            BlogScreen()
        }
        composable(route = SettingsScreen.route) {
            val context = LocalContext.current
            context.startActivity(Intent(context, Settings::class.java))
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

// Screens with tab row
enum class BottomBarTab(
    val route: String
) {
    BLOG(
        Blog.route
    ),
    EXPLORE(
        ExploreScreen.route
    ),
    SCHEDULE(
        Schedule.route
    ),
    SETTINGS(
        SettingsScreen.route
    )
}

fun shouldShowBottomBar(route: String) = route in bottomBarRoutes