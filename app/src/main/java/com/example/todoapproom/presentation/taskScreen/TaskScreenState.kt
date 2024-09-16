package com.example.todoapproom.presentation.taskScreen

sealed class TaskScreenState {
    data object Initial: TaskScreenState()

    data object Loading: TaskScreenState()

    data class Error(val error:String): TaskScreenState()

    data class Success(val successMessage:String): TaskScreenState()
}