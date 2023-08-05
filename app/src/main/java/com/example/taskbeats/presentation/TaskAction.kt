package com.example.taskbeats.presentation

import com.example.taskbeats.data.local.local.Task
import java.io.Serializable

enum class ActionType : Serializable {
    DELETE,
    UPDATE,
    CREATE
}
data class TaskAction(
    val task: Task?,
    val actionType: String
) : Serializable