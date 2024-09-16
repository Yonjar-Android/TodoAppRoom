package com.example.todoapproom.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.data.service.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "TasksDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao {
        return db.taskDao()
    }
}