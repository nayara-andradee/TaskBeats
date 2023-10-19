package com.example.taskbeats.data.local.remote

import com.example.taskbeats.BuildConfig
import retrofit2.http.GET

interface NewsService {

    @GET("top?api_token=${BuildConfig.API_KEY}&locale=us")
    suspend fun fetchTopNews(): NewsResponse


    @GET("top?api_token=${BuildConfig.API_KEY}")
    suspend fun fetchAllNews(): NewsResponse
}
