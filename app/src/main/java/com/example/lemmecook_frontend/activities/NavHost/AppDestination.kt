package com.example.lemmecook_frontend.activities.NavHost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface AppDestination {
    val icon: ImageVector
    val route: String
}

data object LandingScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "LandingScreen"
}

data object SignInScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "SignInScreen"
}

data object SignUpScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "SignUpScreen"
}

data object ForgetPasswordScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "ForgetPasswordScreen"
}

data object ChooseNameScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "ChooseNameScreen"
}

data object OnboardScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "OnboardScreen"
}

data object Step1Screen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "Step1Screen"
}

data object Step2Screen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "Step2Screen"
}

data object RecipeOverviewScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "RecipeOverviewScreen"
}

data object RecipePrepScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "RecipePrepScreen"
}

data object RecipeCongratsScreen : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "RecipeCongratsScreen"
}

data object ExploreScreen : AppDestination {
    override val icon = Icons.Outlined.Explore
    override val route = "Explore"
}

data object SettingsScreen : AppDestination {
    override val icon = Icons.Outlined.Settings
    override val route = "Settings"
}

data object EditProfileScreen : AppDestination {
    override val icon = Icons.Outlined.Explore
    override val route = "EditProfileScreen"
}

data object Schedule : AppDestination {
    override val icon = Icons.Outlined.Schedule
    override val route = "Schedule"
}

data object Blog : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "Blog"
}

// Screens to be displayed in the top TabRow
val AppTabRowScreens = listOf(Blog, ExploreScreen, Schedule, SettingsScreen)