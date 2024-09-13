package com.example.lemmecook_frontend.activities.blog

import kotlinx.serialization.Serializable

@Serializable
data class BlogPost(
    val title: String,
    val body: String,
    val thumbnail: String? = null,
)

@Serializable
data class RssFeed(
    val items: List<BlogPost>
)
