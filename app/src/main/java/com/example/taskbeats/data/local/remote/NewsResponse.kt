package com.example.taskbeats.data.local.remote

import com.google.gson.annotations.SerializedName

//serealisando a resposta do Back-End
data class NewsResponse (
    val data: List<NewsDto>
        )

data class NewsDto (
        @SerializedName("uuid")
        val id: String,
        @SerializedName("snippet")
        val content: String,
        @SerializedName("image_url")
        val imageUrl: String,
        val title: String,
        )