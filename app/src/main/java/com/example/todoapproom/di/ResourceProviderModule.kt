package com.example.todoapproom.di

import android.content.Context
import com.example.todoapproom.data.repositories.RepositoryCRUDImp
import com.example.todoapproom.data.service.dao.TaskDao
import com.example.todoapproom.domain.repositories.IRepositoryCRUD
import com.example.todoapproom.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceProviderModule {

    @Singleton
    @Provides
    fun getResourceProvider(@ApplicationContext context: Context):ResourceProvider{
        return ResourceProvider(context)
    }

    @Singleton
    @Provides
    fun providesRepositoryCrudImp(taskDao: TaskDao, resourceProvider: ResourceProvider): IRepositoryCRUD{
        return RepositoryCRUDImp(taskDao, resourceProvider)
    }
}