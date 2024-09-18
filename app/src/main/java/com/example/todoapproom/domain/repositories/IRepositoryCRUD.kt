package com.example.todoapproom.domain.repositories

import com.example.todoapproom.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface IRepositoryCRUD {
    fun getTasks(): Flow<List<TaskModel>>

    suspend fun createTask(taskModel: TaskModel): String

    suspend fun updateTask(taskModel: TaskModel): String

    suspend fun deleteTask(taskModel: TaskModel): String
}