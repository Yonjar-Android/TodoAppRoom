package com.example.todoapproom.di

import android.content.Context
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
}