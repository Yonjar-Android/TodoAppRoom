package com.example.todoapproom.domain.repositories

import com.example.todoapproom.domain.model.TaskModel

interface IRepositoryCRUD {
    suspend fun getTasks(): List<TaskModel>

    suspend fun createTask(taskModel: TaskModel)

    suspend fun updateTask(taskModel: TaskModel)

    suspend fun deleteTask(taskModel: TaskModel)
}