package com.example.lemmecook_frontend.activities.blog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.ui.theme.sf_pro_display
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

@Composable
fun BlogScreen() {
    val blogPosts = remember { mutableStateListOf<BlogPost>() }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        fetchAndDisplayBlogPosts(
            scope = this,
            rssUrl = "https://www.androidauthority.com/feed",
            blogPosts = blogPosts,
            isLoading = isLoading,
            error = error)
    }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomFontText(text = "News Blog", fontSize = 24.sp, fontWeight = FontWeight.Bold)


            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading.value) {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.displayMedium
                    )
                } else if (error.value != null) {
                    Text("Error: ${error.value}", color = Color.Black)
                } else {
                    BlogPostList(blogPosts)
                }
            }
        }
    }
}

@Composable
fun BlogPostList(blogPosts: List<BlogPost>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(blogPosts) { post ->
            BlogPostCard(post)
        }
    }
}

@Composable
fun BlogPostCard(post: BlogPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(post.thumbnail),
                contentDescription = "Blog Post Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body.take(100) + "...", // Show truncated body text
                style = MaterialTheme.typography.displayMedium,
                maxLines = 2, // Limit lines to 2 for a more compact preview
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun fetchAndDisplayBlogPosts(
    scope: CoroutineScope,
    rssUrl: String,
    blogPosts: MutableList<BlogPost>,
    isLoading: MutableState<Boolean>,
    error: MutableState<String?>
) {
    scope.launch {
        isLoading.value = true
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(rssUrl)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val body = response.body?.string()
                if (body != null) {
                    val rssFeed = Json.decodeFromString<RssFeed>(body)
                    blogPosts.addAll(rssFeed.items)
                }
            } else {
                error.value = response.message
            }
        } catch (e: Exception) {
            error.value = e.message
        } finally {
            isLoading.value = false
        }
    }
}

//@Composable
//fun BlogScreen() {
//    Surface(color = Color.White) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            CustomFontText(text = "News Blog", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//        }
//
//    }
//}

@Composable
fun CustomFontText(
    text: String,
    fontWeight: FontWeight = FontWeight.SemiBold,
    color: Color = colorResource(id = R.color.gr_text),
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontFamily = sf_pro_display,
        fontSize = fontSize,
        color = color
    )
}


@Preview(showBackground = true)
@Composable
fun BlogScreenPreview() {
    BlogScreen()
}