package com.example.todoapproom.data.service.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapproom.data.models.TaskRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("Select * FROM task_table where task_completed == 0")
    fun getAll(): PagingSource<Int,TaskRoomModel>

    @Query(
        """
    SELECT * FROM task_table 
    WHERE task_completed = 1 
    AND date_completed >= strftime('%s', 'now', '-7 days') * 1000
    """
    )
    fun getTaskCompleted(): PagingSource<Int,TaskRoomModel>

    @Insert
    suspend fun insertTask(task: TaskRoomModel)

    @Update
    suspend fun updateTask(task: TaskRoomModel)

    @Delete
    suspend fun deleteTask(task: TaskRoomModel)
}