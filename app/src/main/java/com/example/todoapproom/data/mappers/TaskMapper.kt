package com.example.todoapproom.data.mappers

import com.example.todoapproom.data.models.TaskRoomModel
import com.example.todoapproom.domain.model.TaskModel

// Objeto TaskMapper para la conversión de tipo de TaskModel a TaskRoomModel y viceversa

object TaskMapper {
    fun fromRoomModel(taskRoomModel: TaskRoomModel): TaskModel {
        return TaskModel(
            taskId = taskRoomModel.tId,
            taskName = taskRoomModel.task ?: "",
            isCompleted = taskRoomModel.isCompleted ?: false,
            creationDate = taskRoomModel.creationDate ?: 0L,
            completedDate = taskRoomModel.dateCompleted ?: 0L
        )
    }

    fun toRoomModel(taskModel: TaskModel): TaskRoomModel {
        return TaskRoomModel(
            tId = taskModel.taskId,
            task = taskModel.taskName,
            isCompleted = taskModel.isCompleted,
            creationDate = taskModel.creationDate,
            dateCompleted = taskModel.completedDate
        )
    }
}