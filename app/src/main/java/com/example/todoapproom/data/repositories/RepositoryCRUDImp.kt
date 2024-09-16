package com.example.todoapproom.data.repositories

import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import javax.inject.Inject

class RepositoryCRUDImp @Inject constructor(private val taskDao: TaskDao) : IRepositoryCRUD {
    override suspend fun getTasks(): List<TaskModel> {
        return taskDao.getAll().map { TaskMapper.fromRoomModel(it) }
    }

    override suspend fun createTask(taskModel: TaskModel) {
        taskDao.insertTask(
            TaskMapper.toRoomModel(taskModel)
        )
    }

    override suspend fun updateTask(taskModel: TaskModel) {
        taskDao.updateTask(
            TaskMapper.toRoomModel(taskModel)
        )
    }

    override suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.deleteTask(
            TaskMapper.toRoomModel(taskModel)
        )
    }
}