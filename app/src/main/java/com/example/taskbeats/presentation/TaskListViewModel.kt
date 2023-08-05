package com.example.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskbeats.TaskBeatsApplication
import com.example.taskbeats.data.local.local.Task
import com.example.taskbeats.data.local.local.TaskDao

class TaskListViewModel(taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDao.getAll()

    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }

    }
