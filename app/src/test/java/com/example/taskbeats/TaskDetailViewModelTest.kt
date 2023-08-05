package com.example.taskbeats

//import MainDispatcherRule
import com.example.taskbeats.data.local.local.Task
import com.example.taskbeats.data.local.local.TaskDao
import com.example.taskbeats.presentation.ActionType
import com.example.taskbeats.presentation.TaskAction
import com.example.taskbeats.presentation.TaskDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val taskDao: TaskDao = mock()

    private val underTest: TaskDetailViewModel by lazy {
        TaskDetailViewModel(
            taskDao,
        )
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
