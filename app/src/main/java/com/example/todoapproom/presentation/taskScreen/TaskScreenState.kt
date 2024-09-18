package com.example.todoapproom.presentation.taskScreen

import com.example.todoapproom.domain.model.TaskModel

data class TaskScreenState(
    val isLoading:Boolean = false,
    val tasks: List<TaskModel> = emptyList(),
    val error:String? = null,
    val successMessage:String? = null
)