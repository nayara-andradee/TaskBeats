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

    //liveData mutable
    private val _newsListLiveData = MutableLiveData<List<NewsDto>>()
    val newsListLiveData: LiveData<List<NewsDto>> = _newsListLiveData

    //iniciou o viewModel ele faz a chamada para o back-end
    init {
        getNewsList()
    }

    private fun getNewsList(){
        viewModelScope.launch {
            //caso tenha algum erro com a internet... colocamos try/catch para o app nao da crech
            try {
                val topNews = newsService.fetchTopNews().data
                val allNews = newsService.fetchAllNews().data
                _newsListLiveData.value = topNews + allNews
            } catch (ex: Exception){
                ex.printStackTrace()
            }

        }

    }

    //criando o service dentro do viewmodel
    companion object {
        fun create(): NewsListViewModel {
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }
    }
}