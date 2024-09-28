package com.example.todoapproom.presentation.taskScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.todoapproom.repositorie.FakeRepositoryCRUD
import com.example.todoapproom.ui.theme.TodoAppRoomTheme
import org.junit.Rule
import org.junit.Test

class TaskScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun taskScreen_displaysTasksCorrectly() {
        val taskViewModel = TaskViewModel(FakeRepositoryCRUD()) // Usamos el FakeRepository

        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskScreen(viewModel = taskViewModel)
            }
        }

        // Verifica que se muestran correctamente las tareas del FakeRepository
        composeTestRule.onNodeWithText("Task 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Task 2").assertIsDisplayed()
    }

    @Test
    fun taskScreen_showsAddTaskButton() {
        val taskViewModel = TaskViewModel(FakeRepositoryCRUD())

        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskScreen(viewModel = taskViewModel)
            }
        }

        // Verificamos que el botón FAB para agregar tarea está presente
        composeTestRule.onNodeWithTag("FabAddButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("FabAddButton").performClick()
    }

    @Test
    fun testTaskScreenMenuAndDialogs() {
        val taskViewModel = TaskViewModel(FakeRepositoryCRUD())

        // Set the content to TaskScreen only
        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskScreen(viewModel = taskViewModel)
            }
        }

        // Verifica si el menú desplegable aparece y reacciona al clic
        composeTestRule.onNodeWithTag("1threePointsIcon", useUnmergedTree = true)
            .performClick()

        // Verifica si se muestra el botón de editar en el menú desplegable
        composeTestRule.onNodeWithText("Edit")
            .assertExists()
            .performClick()

        // Verifica si se abre el diálogo de creación de tarea después de hacer clic en "Editar"
        composeTestRule.onNodeWithTag("editTaskButton")
            .assertExists()
            .assertIsDisplayed()

        // Verifica si el botón para cerrar el diálogo funciona correctamente
        composeTestRule.onNodeWithTag("closeButton", useUnmergedTree = true)
            .performClick()

        // Verifica que el diálogo se cerró
        composeTestRule.onNodeWithTag("closeButton")
            .assertDoesNotExist()

        // Verifica si el botón de eliminar aparece en el menú desplegable
        composeTestRule.onNodeWithText("Delete")
            .assertExists()
            .performClick()

        // Verifica si se abre el diálogo de eliminación de tarea
        composeTestRule.onNodeWithTag("tagDeleteThisTagTest")
            .assertExists()
            .assertIsDisplayed()


        // Simula la confirmación de eliminar la tarea
        composeTestRule.onNodeWithTag("deleteButtonDialog")
            .performClick()

        // Verifica que el diálogo de eliminación se cerró
        composeTestRule.onNodeWithText("tagDeleteThisTagTest")
            .assertIsNotDisplayed()
    }

}