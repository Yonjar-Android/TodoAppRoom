package com.example.todoapproom.repositorie

import androidx.paging.PagingData
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import com.example.todoapproom.motherObject.MotherObjectUITest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepositoryCRUD : IRepositoryCRUD {

    private val tasks = MotherObjectUITest.exampleTasks

    override fun getTasks(): Flow<PagingData<TaskModel>> {
        val pagingData = PagingData.from(tasks)
        return flowOf(pagingData)
    }

    override fun getTasksCompleted(): Flow<PagingData<TaskModel>> {
        val completedTasks = tasks.filter { it.isCompleted }
        val pagingData = PagingData.from(completedTasks)
        return flowOf(pagingData)
    }

    override suspend fun createTask(taskModel: TaskModel): String {
        tasks.add(taskModel)
        return "Task created successfully"
    }

    override suspend fun updateTask(taskModel: TaskModel): String {
        val index = tasks.indexOfFirst { it.taskId == taskModel.taskId }
        if (index != -1) {
            tasks[index] = taskModel
            return "Task updated successfully"
        }
        return "Task not found"
    }

    override suspend fun deleteTask(taskModel: TaskModel): String {
        return if (tasks.remove(taskModel)) {
            "Task deleted successfully"
        } else {
            "Task not found"
        }
    }
}
