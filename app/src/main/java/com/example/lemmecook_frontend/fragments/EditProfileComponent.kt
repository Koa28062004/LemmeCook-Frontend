import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun EditProfileComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(2.dp, Color.Black)) // Add a black border
            .padding(16.dp) // Add inner padding to ensure content is not touching the border
    ) {
        // Edit Profile Text
        Text(
            text = "Edit Profile",
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEditProfile() }
                .padding(8.dp)
        )

        // Divider (Middle line)
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Log Out Text
        Text(
            text = "Log Out",
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLogOut() }
                .padding(8.dp)
        )
    }
}

fun onLogOut() {
    // Implementation for log out
    TODO("Not yet implemented")
}

fun onEditProfile() {
    // Implementation for edit profile
    TODO("Not yet implemented")
}
