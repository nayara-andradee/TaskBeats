package com.example.taskbeats

import com.example.taskbeats.data.Task
import com.example.taskbeats.data.TaskDao
import com.example.taskbeats.presentation.ActionType
import com.example.taskbeats.presentation.TaskAction
import com.example.taskbeats.presentation.TaskListViewModel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.description
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()

    private val underTest: TaskListViewModel by lazy {
        TaskListViewModel(
            taskDao,
            UnconfinedTestDispatcher()
        )
    }

    //Teste cases  Delete_All

    @Test
    fun delete_all() = runTest{
        //Givan
        val taskAction = TaskAction(
            task = null,
            actionType = ActionType.DELETE_ALL.name
        )
        //when
        underTest.execute(taskAction)

        //then
        verify(taskDao).deleteAll()
    }

    @Test
    fun update_task() = runTest{
        //Given
        val task = Task(
            id = 1,
            title = "title",
            description = "description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.UPDATE.name
        )

        //when
        underTest.execute(taskAction)

        //then
        verify(taskDao).update(task)
    }

    @Test
    fun delete_task() = runTest{
        //Given
        val task = Task(
            id = 1,
            title = "title",
            description = "description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.DELETE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).deleteById(task.id)
    }

    @Test
    fun create_task() = runTest {
        //Given
        val task = Task(
            id = 1,
            title = "title",
            description = "description"
        )
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.CREATE.name
        )

        //When
        underTest.execute(taskAction)

        //Then
        verify(taskDao).insert(task)
    }
}