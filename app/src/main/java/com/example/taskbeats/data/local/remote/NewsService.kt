package com.example.taskbeats.data.local.remote

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("news?category=science")
    suspend fun fetchNews(): NewsResponse
}
