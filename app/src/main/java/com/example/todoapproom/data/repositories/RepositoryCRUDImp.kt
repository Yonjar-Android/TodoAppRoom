package com.example.todoapproom.data.repositories

import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryCRUDImp @Inject constructor(private val taskDao: TaskDao) : IRepositoryCRUD {
    override fun getTasks(): Flow<List<TaskModel>> {
        return try {
            taskDao.getAll().map { taskRoom ->
                taskRoom.map { taskRoomModel ->
                    TaskMapper.fromRoomModel(taskRoomModel)
                }
            }
        }catch (e:Exception){
            flowOf(listOf())
        }
    }

    override fun getTasksCompleted(): Flow<List<TaskModel>> {
        return try {
            taskDao.getTaskCompleted().map { taskRoom ->
                taskRoom.map { taskRoomModel ->
                    TaskMapper.fromRoomModel(taskRoomModel)
                }
            }
        }catch (e:Exception){
            flowOf(listOf())
        }
    }

    override suspend fun createTask(taskModel: TaskModel): String {
        return try {
            taskDao.insertTask(TaskMapper.toRoomModel(taskModel))
            "Tarea creada exitosamente"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): String {
        return try {
            taskDao.updateTask(
                TaskMapper.toRoomModel(taskModel)
            )
            "Tarea actualizada exitosamente"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    override suspend fun deleteTask(taskModel: TaskModel): String {

        return try {
            taskDao.deleteTask(
                TaskMapper.toRoomModel(taskModel)
            )
            "Tarea eliminada exitosamente"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }

    }
}