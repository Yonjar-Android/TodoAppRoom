package com.example.todoapproom.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskRoomModel(
    @PrimaryKey(autoGenerate = true) val tId:Int,
    @ColumnInfo("task_name") val task: String?,
    @ColumnInfo("task_date") val creationDate:Long?,
    @ColumnInfo("task_completed") val isCompleted:Boolean?,
    @ColumnInfo(name = "date_completed") val dateCompleted: Long?
)

