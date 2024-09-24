package com.example.todoapproom.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.todoapproom.R
import com.example.todoapproom.data.mappers.TaskMapper
import com.example.todoapproom.data.models.TaskRoomModel
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import com.example.todoapproom.utils.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryCRUDImp @Inject constructor(
    private val taskDao: TaskDao,
    private val resourceProvider: ResourceProvider
) : IRepositoryCRUD {
    override fun getTasks(): Flow<PagingData<TaskModel>> {
        return Pager(
                PagingConfig(
                    pageSize = 25,
                    prefetchDistance = 10,
                ),
                pagingSourceFactory = {
                    taskDao.getAll()
                }
            ).flow.map { value: PagingData<TaskRoomModel> ->
                value.map { task: TaskRoomModel ->
                    TaskMapper.fromRoomModel(task)
                }
            }
    }

    override fun getTasksCompleted(): Flow<PagingData<TaskModel>> {
        return Pager(
            PagingConfig(
                pageSize = 25,
                prefetchDistance = 10
            ),
            pagingSourceFactory = {taskDao.getTaskCompleted()}
        ).flow.map { value: PagingData<TaskRoomModel> ->
            value.map {
                TaskMapper.fromRoomModel(it)
            }
        }
    }

    override suspend fun createTask(taskModel: TaskModel): String {
        return try {
            taskDao.insertTask(TaskMapper.toRoomModel(taskModel))
            resourceProvider.getString(R.string.task_created_success)
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    override suspend fun updateTask(taskModel: TaskModel): String {
        return try {
            taskDao.updateTask(
                TaskMapper.toRoomModel(taskModel)
            )
            resourceProvider.getString(R.string.task_updated_success)
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    override suspend fun deleteTask(taskModel: TaskModel): String {

        return try {
            taskDao.deleteTask(
                TaskMapper.toRoomModel(taskModel)
            )
            resourceProvider.getString(R.string.task_deleted_success)
        } catch (e: Exception) {
            "Error: ${e.message}"
        }

    }
}