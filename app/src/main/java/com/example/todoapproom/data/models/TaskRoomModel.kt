package com.example.todoapproom.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapproom.domain.model.TaskModel

@Entity(tableName = "task_table")
data class TaskRoomModel(
    @PrimaryKey(autoGenerate = true) val tId:Int,
    @ColumnInfo("task_name") val task: String?,
    @ColumnInfo("task_date") val creationDate:Long?,
    @ColumnInfo("task_completed") val isCompleted:Boolean?
)

