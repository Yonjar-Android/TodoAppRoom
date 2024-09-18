package com.example.todoapproom.motherObject

import com.example.todoapproom.data.mappers.TaskMapper
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

    val task2 = TaskModel(
        taskId = 2,
        creationDate = 3000L,
        taskName = "Programar SQL",
        isCompleted = true
    )

    val taskList = listOf(task, task2)

    val taskRoomList = listOf(TaskMapper.toRoomModel(task), TaskMapper.toRoomModel(task2))
}