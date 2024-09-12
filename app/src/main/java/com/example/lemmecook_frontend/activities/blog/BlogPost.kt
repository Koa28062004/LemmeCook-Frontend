package com.example.lemmecook_frontend.activities.blog

import kotlinx.serialization.Serializable

@Serializable
data class BlogPost(
    val title: String,
    val thumbnail: String? = null,
    val body: String
)

@Serializable
data class RssFeed(
    val items: List<BlogPost>
)
