package com.example.lemmecook_frontend.activities.NavHost

import androidx.annotation.Nullable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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

data object Home : AppDestination {
    override val icon = Icons.Outlined.Home
    override val route = "Home"
}

data object Booking : AppDestination {
    override val icon = Icons.Outlined.ConfirmationNumber
    override val route = "Booking"
}

data object Notification : AppDestination {
    override val route = "Notification"
    override val icon = Icons.Outlined.Notifications
}

data object Account : AppDestination {
    override val icon = Icons.Outlined.Person
    override val route = "Account"
}

data object ComingSoon : AppDestination {
    override val icon = Icons.Outlined.ConfirmationNumber
    override val route = "ComingSoon"
}

// Screens to be displayed in the top BookingTabRow
val AppTabRowScreens = listOf(Home, Booking, Notification, Account)