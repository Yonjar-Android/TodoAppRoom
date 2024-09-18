package com.example.todoapproom.presentation.taskScreen

data class TaskScreenState(
    val isLoading:Boolean = false,
    val error:String? = null,
    val successMessage:String? = null
)