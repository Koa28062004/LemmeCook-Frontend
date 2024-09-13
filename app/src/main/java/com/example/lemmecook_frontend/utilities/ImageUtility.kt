package com.example.lemmecook_frontend.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.draw.clip
import coil.compose.rememberImagePainter
import androidx.compose.ui.res.painterResource
import com.example.lemmecook_frontend.R
import java.io.File

@Composable
fun AvatarPicker(
    avatarUri: Uri?,
    onImagePicked: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onImagePicked(uri)
    }

    // Default avatar image resource
    val defaultAvatar = painterResource(id = R.drawable.avatar)

    // Display the avatar
    Image(
        painter = if (avatarUri != null) rememberImagePainter(avatarUri) else defaultAvatar,
        contentDescription = "Avatar",
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable {
                launcher.launch("image/*")
            }
    )
}

object ImageUtility {
    fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "avatar_image")
        file.outputStream().use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        return file
    }
}
