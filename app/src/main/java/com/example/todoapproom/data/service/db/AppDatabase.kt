package com.example.todoapproom.data.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapproom.data.models.TaskRoomModel
import com.example.todoapproom.data.service.dao.TaskDao

@Database(
    entities = [TaskRoomModel::class], version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun taskDao(): TaskDao
}