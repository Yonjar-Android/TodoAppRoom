package com.example.todoapproom.data.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapproom.data.models.TaskRoomModel
import com.example.todoapproom.data.service.dao.TaskDao

// Clase de la base de datos Room

@Database(
    entities = [TaskRoomModel::class], version = 2
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun taskDao(): TaskDao
}