package com.example.todoapproom.presentation.taskCompletedScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.todoapproom.repositorie.FakeRepositoryCRUD
import com.example.todoapproom.ui.theme.TodoAppRoomTheme
import org.junit.Rule
import org.junit.Test

class TaskCompletedScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun taskCompletedScreen_displaysTasksCorrectly() {
        val taskViewModel =
            TasksCompletedViewModel(FakeRepositoryCRUD()) // Usamos el FakeRepository

        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskCompletedScreen(viewModel = taskViewModel)
            }
        }

        // Verifica que se muestran correctamente las tareas del FakeRepository
        composeTestRule.onNodeWithText("Task 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Task 4").assertIsDisplayed()
    }

    @Test
    fun taskCompletedScreen_showsDialogCorrectly() {
        val taskViewModel =
            TasksCompletedViewModel(FakeRepositoryCRUD()) // Usamos el FakeRepository

        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskCompletedScreen(viewModel = taskViewModel)
            }
        }

        composeTestRule.onNodeWithTag("Task2").assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithTag("TaskText2").assertIsDisplayed()
    }

    @Test
    fun taskCompletedScreen_checkTasks(){
        val taskViewModel =
            TasksCompletedViewModel(FakeRepositoryCRUD()) // Usamos el FakeRepository

        composeTestRule.setContent {
            TodoAppRoomTheme {
                TaskCompletedScreen(viewModel = taskViewModel)
            }
        }

        composeTestRule.onNodeWithTag("check2").assertIsDisplayed().performClick()

        composeTestRule.onNodeWithTag("check4").assertIsDisplayed().performClick()
    }
}