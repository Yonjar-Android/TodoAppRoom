package com.example.todoapproom.motherObject

import com.example.todoapproom.domain.model.TaskModel

object MotherObjectUITest {
    // Crear una lista de tareas de ejemplo
    val exampleTasks = mutableListOf(
        TaskModel(
            taskId = 1,
            taskName = "Task 1",
            isCompleted = false,
            creationDate = System.currentTimeMillis(),
            completedDate = null
        ),
        TaskModel(
            taskId = 2,
            taskName = "Task 2",
            isCompleted = true,
            creationDate = System.currentTimeMillis() - 86400000L, // Creado hace un día
            completedDate = System.currentTimeMillis() - 43200000L // Completado hace medio día
        ),
        TaskModel(
            taskId = 3,
            taskName = "Task 3",
            isCompleted = false,
            creationDate = System.currentTimeMillis() - 172800000L, // Creado hace dos días
            completedDate = null
        ),
        TaskModel(
            taskId = 4,
            taskName = "Call mom",
            isCompleted = true,
            creationDate = System.currentTimeMillis() - 259200000L, // Creado hace tres días
            completedDate = System.currentTimeMillis() - 86400000L // Completado hace un día
        ),
        TaskModel(
            taskId = 5,
            taskName = "Read a book",
            isCompleted = false,
            creationDate = System.currentTimeMillis() - 3600000L, // Creado hace una hora
            completedDate = null
        )
    )

}