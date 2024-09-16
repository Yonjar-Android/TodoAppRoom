package com.example.todoapproom.domain.model

data class TaskModel(
    val taskId: Int,
    val taskName: String,
    var isCompleted:Boolean,
    val creationDate: Long
)