package com.example.todoapproom.domain.repositories

import androidx.paging.PagingData
import com.example.todoapproom.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface IRepositoryCRUD {
    fun getTasks(): Flow<PagingData<TaskModel>>

    fun getTasksCompleted():  Flow<PagingData<TaskModel>>

    suspend fun createTask(taskModel: TaskModel): String

    suspend fun updateTask(taskModel: TaskModel): String

    suspend fun deleteTask(taskModel: TaskModel): String
}