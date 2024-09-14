import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.fragments.MenuItem
import com.example.lemmecook_frontend.fragments.SetCaloriesGoal
import com.example.lemmecook_frontend.fragments.SetCarbGoal
import com.example.lemmecook_frontend.fragments.SetFatGoal
import com.example.lemmecook_frontend.fragments.SetProteinGoal
import com.example.lemmecook_frontend.fragments.ThreeDotMenu
import com.example.lemmecook_frontend.singleton.GoalSession
import com.example.lemmecook_frontend.singleton.ProgressSession
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Composable
fun ProgressComponent(
    allowChange: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    UserSession.userId = "1"

    var showCaloriesDialog by remember { mutableStateOf(false) }
    var showFatDialog by remember { mutableStateOf(false) }
    var showProteinDialog by remember { mutableStateOf(false) }
    var showCarbDialog by remember { mutableStateOf(false) }

    // Remember state for goal and progress
    var currentGoal by remember { mutableStateOf(GoalSession.goal) }
    var currentProgress by remember { mutableStateOf(ProgressSession.progress) }

    // Fetch goal and progress data when the composable is first composed
    LaunchedEffect(Unit) {
        GoalSession.fetchGoalData(context)
        ProgressSession.fetchProgressData(context)
    }

    // Update state with the fetched data
    LaunchedEffect(GoalSession.goal) {
        currentGoal = GoalSession.goal
    }

    LaunchedEffect(ProgressSession.progress) {
        currentProgress = ProgressSession.progress
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    shadowElevation = 0.5.dp.toPx() // Convert dp to pixels
                    translationY = (-0.8).dp.toPx() // Y-axis translation for the shadow
                }
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(30.dp),
                    clip = false,
                    ambientColor = Color(242, 242, 242), // Custom shadow color with opacity
                    spotColor = Color(242, 242, 242) // Custom shadow color with opacity
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 20.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's Progress",
                        fontFamily = sf_pro_display,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    if (allowChange) {
                        ThreeDotMenu(buttonItems = listOf(
                            MenuItem("Set calories goal") {
                                showCaloriesDialog = true
                            },
                            MenuItem("Set fat goal") {
                                showFatDialog = true
                            },
                            MenuItem("Set protein goal") {
                                showProteinDialog = true
                            },
                            MenuItem("Set carb goal") {
                                showCarbDialog = true
                            }
                        ))
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Calories(currentCalories = currentProgress.calories)

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        NutrientProgress(
                            percentage = (currentProgress.fat * 1f / currentGoal.fat),
                            label = "Fat",
                            color = Color(253, 197, 52) // Gold color
                        )
                        NutrientProgress(
                            percentage = (currentProgress.protein * 1f / currentGoal.protein),
                            label = "Pro",
                            color = Color(52, 133, 253) // Blue color
                        )
                        NutrientProgress(
                            percentage = (currentProgress.carb * 1f / currentGoal.carb),
                            label = "Carb",
                            color = Color(120, 118, 245) // Purple color
                        )
                    }
                }
            }
        }
    }
    if (showCaloriesDialog) {
        SetCaloriesGoal { calories ->
            // Update the goal first
            GoalSession.updateGoal(
                context = context,
                goalData = currentGoal
            )
            // Fetch the updated goal data after updating
            showCaloriesDialog = false
        }
    }

    if (showFatDialog) {
        SetFatGoal { fat ->
            GoalSession.updateGoal(
                context = context,
                goalData = currentGoal
            )
            showFatDialog = false
        }
    }

    if (showProteinDialog) {
        SetProteinGoal { protein ->
            GoalSession.updateGoal(
                context = context,
                goalData = currentGoal
            )
            showProteinDialog = false
        }
    }

    if (showCarbDialog) {
        SetCarbGoal { carb ->
            GoalSession.updateGoal(
                context = context,
                goalData = currentGoal
            )
            showCarbDialog = false
        }
    }
}

@Composable
fun NutrientProgress(percentage: Float, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { percentage },
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .padding(4.dp),
                color = color,
                strokeWidth = 8.dp,
            )
            // Overlay text inside the circular progress indicator
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${(percentage * 100).toInt()}%",
                    fontFamily = sf_pro_display,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = label,
                    fontFamily = sf_pro_display,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun Calories(currentCalories: Float) {
    Column {
        Text(
            text = "Calories",
            fontFamily = sf_pro_display,
            fontSize = 15.sp,
            color = Color.Gray
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_fire),
                contentDescription = "Fire icon illustrating calories",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(18.dp)
            )
            Text(
                text = "$currentCalories",
                fontFamily = sf_pro_display,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressComponent() {
    val allowChange = true
    ProgressComponent(
        allowChange
    )
}