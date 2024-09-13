package com.example.lemmecook_frontend.activities.blog

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.lemmecook_frontend.R
import com.example.lemmecook_frontend.ui.theme.sf_pro_display
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.jsoup.safety.Safelist;

@Composable
fun BlogScreen() {
    val blogPosts = remember { mutableStateListOf<BlogPost>() }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        fetchAndDisplayBlogPosts(
            scope = this,
            rssUrl = "https://pinchofyum.com/feed",
            blogPosts = blogPosts,
            isLoading = isLoading,
            error = error
        )
    }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomFontText(text = "Food Blog", fontSize = 24.sp, fontWeight = FontWeight.Bold)


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
            .wrapContentSize()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.pastelGreen),
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            val imagePainter = rememberAsyncImagePainter(post.thumbnail)

            // Log the state of the image painter
            LaunchedEffect(key1 = imagePainter) {
                // Log the image loading state
                Log.d("ImageLoading", "Image state: ${imagePainter.state}")

                // Check if the image loaded successfully
                if (imagePainter.state is AsyncImagePainter.State.Success) {
                    Log.d("ImageLoading", "Image loaded successfully!")
                } else if (imagePainter.state is AsyncImagePainter.State.Error) {
                    Log.e("ImageLoading", "Image loading failed")
                }
            }

            Image(
                painter = imagePainter,
                contentDescription = "Blog Post Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize(),
            )



            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.darkGreen),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body.take(500) + "...",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3, // Limit lines for a more compact preview
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.darkGreen),
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

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(rssUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                error.value = e.message
                isLoading.value = false
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {

                    val body = response.body?.string()
                    if (body != null) {
                        val rssFeed = parseRssFeed(body)
                        blogPosts.addAll(rssFeed.items)
                    }
                } else {
                    error.value = response.message
                }
                isLoading.value = false
            }
        })
    }
}

@Composable
private fun CustomFontText(
    text: String,
    fontWeight: FontWeight = FontWeight.SemiBold,
    color: Color = colorResource(id = R.color.darkGreen),
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

fun parseRssFeed(rssXml: String): RssFeed {
    val rssItems = mutableListOf<BlogPost>()
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    val doc = dBuilder.parse(InputSource(StringReader(rssXml)))
    doc.documentElement.normalize()

    val items: NodeList = doc.getElementsByTagName("item")

    for (i in 0 until items.length) {
        val item = items.item(i) as Element

        val title = item.getElementsByTagName("title").item(0).textContent
        val descriptionElement = item.getElementsByTagName("description").item(0)
        val body = descriptionElement.textContent  // Get the description text

        // Clean up HTML (optional)
        val bodyText = Jsoup.clean(
            body,
            Safelist.none()
        ) // Remove all HTML rssItems.add(BlogPost(title, bodyText, thumbnail ?: ""))
        var thumbnail: String? = null
        val mediaContents = item.getElementsByTagName("media:content")
        for (j in 0 until mediaContents.length) {
            val mediaContent = mediaContents.item(j) as Element

            // 1. Check for a direct <media:thumbnail> within <media:content>
            val thumbnailElement = mediaContent.getElementsByTagName("media:thumbnail").item(0)
            if (thumbnailElement != null && (thumbnailElement as Element).hasAttribute("url")) { // Cast to Element
                thumbnail = thumbnailElement.getAttribute("url")
                break // Found a thumbnail, stop searching
            }

            // 2. Check for a "url" attribute on the <media:content> itself
            if (mediaContent.hasAttribute("url")) {
                thumbnail = mediaContent.getAttribute("url")
                break // Found a URL, stop searching
            }
        }
        rssItems.add(BlogPost(title, bodyText, thumbnail ?: ""))
    }

    return RssFeed(rssItems)
}

@Preview(showBackground = true)
@Composable
fun BlogScreenPreview() {
    BlogScreen()
}