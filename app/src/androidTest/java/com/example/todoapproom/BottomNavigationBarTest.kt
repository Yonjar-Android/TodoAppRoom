package com.example.todoapproom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapproom.presentation.BottomNavigation
import com.example.todoapproom.presentation.taskCompletedScreen.TaskCompletedScreen
import com.example.todoapproom.presentation.taskCompletedScreen.TasksCompletedViewModel
import com.example.todoapproom.presentation.taskScreen.TaskScreen
import com.example.todoapproom.presentation.taskScreen.TaskViewModel
import com.example.todoapproom.repositorie.FakeRepositoryCRUD
import com.example.todoapproom.ui.theme.TodoAppRoomTheme
import org.junit.Rule
import org.junit.Test

class BottomNavigationBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomNavigation() {
        val taskViewModel = TaskViewModel(FakeRepositoryCRUD())
        val taskCompletedViewModel = TasksCompletedViewModel(FakeRepositoryCRUD())

        composeTestRule.setContent {
            TodoAppRoomTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navHostController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "TaskScreenNav",
                        modifier = Modifier.padding(it)
                    ) {
                        composable("TaskScreenNav") {
                            TaskScreen(taskViewModel)
                        }

                        composable("TaskScreenCompletedNav") {
                            TaskCompletedScreen(taskCompletedViewModel)
                        }
                    }
                }
            }
        }

        // Verifica si se navega correctamente al pulsar el icono de la lista de tareas
        composeTestRule.onNodeWithContentDescription("First icon of the menu called: task list")
            .performClick()

        // Verifica si se navega correctamente al pulsar el icono de tareas completadas
        composeTestRule.onNodeWithContentDescription("Second icon of the menu called: completed tasks")
            .performClick()
    }

    @Test
    fun testNavigationToTaskCompletedScreen() {
        val taskViewModel = TaskViewModel(FakeRepositoryCRUD())
        val taskCompletedViewModel = TasksCompletedViewModel(FakeRepositoryCRUD())

        composeTestRule.setContent {
            TodoAppRoomTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navHostController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "TaskScreenNav",
                        modifier = Modifier.padding(it)
                    ) {
                        composable("TaskScreenNav") {
                            TaskScreen(taskViewModel)
                        }

                        composable("TaskScreenCompletedNav") {
                            TaskCompletedScreen(taskCompletedViewModel)
                        }
                    }
                }
            }
        }

        // Verifica si se navega correctamente al pulsar el icono de tareas completadas
        composeTestRule.onNodeWithContentDescription("Second icon of the menu called: completed tasks")
            .performClick()

        // Aquí puedes verificar que el contenido de la pantalla de tareas completadas se muestra correctamente
        composeTestRule.onNodeWithTag("TaskCompletedScreen").assertIsDisplayed()
    }
}


