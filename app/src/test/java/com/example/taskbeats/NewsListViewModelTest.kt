package com.example.taskbeats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskbeats.data.local.remote.NewsDto
import com.example.taskbeats.data.local.remote.NewsResponse
import com.example.taskbeats.data.local.remote.NewsService
import com.example.taskbeats.presentation.NewsListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsListViewModelTest {

    //para tirar do dispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    //para tirar do liveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val service: NewsService = mock()
    private lateinit var underTest : NewsListViewModel

    @Test
    fun `GIVEN request succeed news WHEN fetch THEN return list`(){
        runBlocking {

            // GIVEN
        val expected = listOf(
            NewsDto(
                id = "id1",
                content = "content1",
                imageUrl = "image1",
                title = "title"
            )
        )
        val response = NewsResponse(data = expected, category = "tech")
        whenever(service.fetchNews()).thenReturn(response)

            // WHEN
            underTest = NewsListViewModel(service)
            val result = underTest.newsListLiveData.getOrAwaitValue()

            // THEN
            assert(result == expected)

    }

    }
}