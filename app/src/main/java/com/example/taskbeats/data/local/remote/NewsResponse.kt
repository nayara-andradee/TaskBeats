package com.example.taskbeats.data.local.remote

data class NewsResponse (
    val category: String,
    val data: List<NewsDto>
        )

data class NewsDto (
        val id: String,
        val content: String,
        val imageUrl: String,
        val title: String,
        )