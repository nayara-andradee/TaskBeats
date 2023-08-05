package com.example.taskbeats

import com.example.taskbeats.data.local.local.TaskDao
import com.example.taskbeats.presentation.TaskListViewModel
import org.mockito.kotlin.mock

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()

    private val underTest: TaskListViewModel by lazy {
        TaskListViewModel(
            taskDao
        )
    }
}