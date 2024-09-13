import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.example.lemmecook_frontend.fragments.ThreeDotMenu
import com.example.lemmecook_frontend.singleton.GoalSession
import com.example.lemmecook_frontend.singleton.ProgressSession
import com.example.lemmecook_frontend.singleton.UserSession
import com.example.lemmecook_frontend.ui.theme.sf_pro_display

@Composable
fun ProgressComponent(
    currentCalo: Int,
    currentFat: Int,
    currentPro: Int,
    currentCarb: Int,
    goalFat: Int,
    goalPro: Int,
    goalCarb: Int,
    allowChange: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    UserSession.userId = "1"
    val goalSession = remember { GoalSession }

    // Fetch goal data when the composable is first composed
    LaunchedEffect(Unit) {
        goalSession.fetchGoalData(context)
    }

    // Observe goal data changes
    val currentGoal = goalSession.goal

    // Log the progress data
    LaunchedEffect(currentGoal) {
        ProgressSession.fetchProgressData(context)
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
                        // TODO: Implement updating nutrition progress
                        ThreeDotMenu(buttonItems = listOf(
                            MenuItem("Set calories goal") {},
                            MenuItem("Set fat goal") {},
                            MenuItem("Set protein goal") {},
                            MenuItem("Set carb goal") {}
                        ))
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Calories(currentCalories = currentCalo)

                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        NutrientProgress(
                            percentage = (currentFat * 1f / currentGoal.fat),
                            label = "Fat",
                            color = Color(253, 197, 52) // Gold color
                        )
                        NutrientProgress(
                            percentage = (currentPro * 1f / currentGoal.protein),
                            label = "Pro",
                            color = Color(52, 133, 253) // Blue color
                        )
                        NutrientProgress(
                            percentage = (currentCarb * 1f / currentGoal.carb),
                            label = "Carb",
                            color = Color(120, 118, 245) // Purple color
                        )
                    }
                }
            }
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
fun Calories(currentCalories: Int) {
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
    val currentCalories = 1284
    val currentFat = 290
    val currentPro = 650
    val currentCarb = 850
    val goalFat = 1000
    val goalPro = 1000
    val goalCarb = 1000
    val allowChange = true
    ProgressComponent(
        currentCalories,
        currentFat,
        currentPro,
        currentCarb,
        goalFat,
        goalPro,
        goalCarb,
        allowChange
    )
}