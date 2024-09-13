package com.example.lemmecook_frontend.fragments

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.activities.NavHost.AppDestination

//@RequiresApi(Build.VERSION_CODES.O)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun TabRowFragment() {
//    val navController = rememberNavController()
//    val currentBackStack by navController.currentBackStackEntryAsState()
//    val currentDestination = currentBackStack?.destination
//    var previousScreen by remember { mutableStateOf<AppDestination>(Home) }
//
//    LaunchedEffect(currentDestination?.route) {
//        val newScreen = AppTabRowScreens.find { it.route == currentDestination?.route }
//        newScreen?.let {
//            previousScreen = it
//        }
//    }
//
//    val currentScreen =
//        AppTabRowScreens.find { it.route == currentDestination?.route } ?: previousScreen
//    Scaffold(
//        bottomBar = {
//            if (shouldShowBottomBar(currentDestination?.route.toString())) {
//                AppTabRow(
//                    allScreens = AppTabRowScreens,
//                    onTabSelected = { newScreen -> navController.navigateSingleTopTo(newScreen.route) },
//                    currentScreen = currentScreen
//                )
//            }
//        }
//    ) { innerPadding ->
//        AppNavHost(
//            navController = navController,
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}


@Composable
fun AppTabRow(
    allScreens: List<AppDestination>,
    onTabSelected: (AppDestination) -> Unit,
    currentScreen: AppDestination
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.selectableGroup()
        ) {
            allScreens.forEach { screen ->
                AppTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun AppTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = colorResource(id = R.color.bg_green)
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
            .background(
                if (selected) tabTintColor.copy(alpha = 0.7f)
                else Color.Transparent, RoundedCornerShape(4.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(4.dp)
                .clickable(onClick = onSelected)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        if (selected) {
            Text(
//                text = text.uppercase(Locale.getDefault()),
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 12.sp
            )
        }
    }
}


private val TabHeight = 70.dp
private const val InactiveTabOpacity = 0.60f

//private const val TabFadeInAnimationDuration = 150
//private const val TabFadeInAnimationDelay = 100
//private const val TabFadeOutAnimationDuration = 100

private const val TabFadeInAnimationDuration = 10
private const val TabFadeInAnimationDelay = 10
private const val TabFadeOutAnimationDuration = 10
