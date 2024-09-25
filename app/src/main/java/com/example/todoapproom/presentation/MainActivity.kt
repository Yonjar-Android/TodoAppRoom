package com.example.todoapproom.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapproom.R
import com.example.todoapproom.presentation.clockScreen.ClockScreen
import com.example.todoapproom.presentation.taskCompletedScreen.TaskCompletedScreen
import com.example.todoapproom.presentation.taskCompletedScreen.TasksCompletedViewModel
import com.example.todoapproom.presentation.taskScreen.TaskScreen
import com.example.todoapproom.presentation.taskScreen.TaskViewModel
import com.example.todoapproom.ui.theme.TodoAppRoomTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {

            // Implementación del cambio de color de la barra de estado por si el dispositivo
            // está en modo claro u oscuro

            val useDarkIcons = !isSystemInDarkTheme() // Invertir el color de los íconos respecto al tema
            val statusBarColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Color.White
            }

            window.apply {
                // Usar decorView para trabajar con WindowInsetsController
                WindowCompat.setDecorFitsSystemWindows(this, false)

                // Controlador para los insets de la ventana
                val controller = WindowInsetsControllerCompat(this, decorView)

                // Establecer si los íconos de la barra de estado deben ser oscuros o claros
                controller.isAppearanceLightStatusBars = useDarkIcons

                // Establecer el color de fondo de la barra de estado
                decorView.setBackgroundColor(statusBarColor.toArgb())
            }


            val taskViewModel: TaskViewModel by viewModels()

            val tasksCompletedViewModel: TasksCompletedViewModel by viewModels()

            TodoAppRoomTheme {


                val navController = rememberNavController()

                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues()),
                    content = {
                        // NavHost para determinar las rutas y pantallas para navegar
                        NavHost(navController = navController, startDestination = "TaskScreenNav",
                            modifier = Modifier.padding(it)){
                            composable("TaskScreenNav"){
                                TaskScreen(taskViewModel)
                            }

                            composable("TaskScreenCompletedNav"){
                                TaskCompletedScreen(tasksCompletedViewModel)
                            }

                            composable("ClockScreenNav"){
                                ClockScreen()
                            }
                        }
                    },
                    bottomBar = {BottomNavigation(navController)}
                )
            }
        }
    }
}

// Componente de diseño y configuración de la navegación en la aplicación
@Composable
fun BottomNavigation(navHostController: NavHostController){
    var selectedIcon by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
        NavigationBarItem(selected = selectedIcon == 0, onClick = {
            selectedIcon = 0
            navHostController.navigate("TaskScreenNav")
        }, icon = {
            IconMenu(
                R.drawable.list_solid, "First icon of the menu called: task list",
            )

        })

        NavigationBarItem(selected = selectedIcon == 1, onClick = {
            selectedIcon = 1
            navHostController.navigate("ClockScreenNav")
        }, icon = {
            IconMenu(R.drawable.clock_solid, "Second icon of the menu called: clock")
        })

        NavigationBarItem(selected = selectedIcon == 2, onClick = {
            selectedIcon = 2
            navHostController.navigate("TaskScreenCompletedNav")
        }, icon = {
            IconMenu(R.drawable.list_check_solid, "Third icon of the menu called: completed tasks")
        })
    }
}

// Composable para los íconos a usar en la barra de navegación
@Composable
fun IconMenu(@DrawableRes image:Int, description:String){
    Icon(painter = painterResource(id = image), contentDescription = description,
        modifier = Modifier.size(35.dp), tint = MaterialTheme.colorScheme.onSurface)
}

