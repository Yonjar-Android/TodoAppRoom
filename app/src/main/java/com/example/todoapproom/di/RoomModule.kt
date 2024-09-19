package com.example.todoapproom.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.data.service.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Modulo de inyección de dependencias para proveer instancia de la base de datos y sus DAOS

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Agregar la nueva columna 'date_completed' a la tabla 'task_table'
            database.execSQL("ALTER TABLE task_table ADD COLUMN date_completed INTEGER DEFAULT NULL")
        }
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "TasksDB"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: AppDatabase): TaskDao {
        return db.taskDao()
    }
}