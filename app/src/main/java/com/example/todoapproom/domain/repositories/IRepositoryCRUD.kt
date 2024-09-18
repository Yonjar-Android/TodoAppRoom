package com.example.todoapproom.domain.repositories

import com.example.todoapproom.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface IRepositoryCRUD {
    fun getTasks(): Flow<List<TaskModel>>

    suspend fun createTask(taskModel: TaskModel)

    suspend fun updateTask(taskModel: TaskModel)

    suspend fun deleteTask(taskModel: TaskModel)
}