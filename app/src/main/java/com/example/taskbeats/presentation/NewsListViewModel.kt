package com.example.taskbeats.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskbeats.data.local.remote.NewsDto
import com.example.taskbeats.data.local.remote.NewsService
import com.example.taskbeats.data.local.remote.RetrofitModule
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsService: NewsService
): ViewModel() {

    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()
    val newsListLiveData: LiveData<List<NewsDto>> = _newsListLiveData

    init {
        getNewsList()
    }

    private fun getNewsList(){
        viewModelScope.launch {
            //caso o app crech try/catch
            try {
                val topNews = newsService.fetchTopNews().data
                val allNews = newsService.fetchAllNews().data
                _newsListLiveData.value = topNews + allNews
            } catch (ex: Exception){
                ex.printStackTrace()
            }

        }

    }

    companion object {
        fun create(): NewsListViewModel {
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }
    }
}