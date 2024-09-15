package com.example.todoapproom.domain.model

data class TaskModel(
    val taskId: Long,
    val taskName: String,
    var isCompleted:Boolean
)