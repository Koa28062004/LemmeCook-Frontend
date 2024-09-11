package com.example.lemmecook_frontend.activities.blog

data class BlogPost(
    val title: String,
    val author: String,
    val date: Long,
    val imageUrl: String,
    val content: String,
    val link: String
)