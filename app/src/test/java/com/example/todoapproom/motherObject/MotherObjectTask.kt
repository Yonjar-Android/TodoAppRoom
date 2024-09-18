package com.example.todoapproom.motherObject

import com.example.todoapproom.domain.model.TaskModel

object MotherObjectTask {

    const val message = "A success message"
    const val error = "An error Message"

    val task = TaskModel(
        taskId = 1,
        creationDate = 1000L,
        taskName = "Programar Python",
        isCompleted = false
    )

    val taskList = listOf(
        task,
        TaskModel(
            taskId = 2,
            creationDate = 3000L,
            taskName = "Programar SQL",
            isCompleted = true
        ),
        TaskModel(
            taskId = 3,
            creationDate = 7000L,
            taskName = "Programar C#",
            isCompleted = false
        ),
    )
}