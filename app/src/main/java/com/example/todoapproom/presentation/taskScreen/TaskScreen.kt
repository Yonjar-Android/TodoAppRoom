package com.example.todoapproom.presentation.taskScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.todoapproom.R
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.ui.theme.bgColor
import com.example.todoapproom.ui.theme.buttonColor
import com.example.todoapproom.utils.SoundPlayer

@Composable
fun TaskScreen(viewModel: TaskViewModel) {

    val context = LocalContext.current

    val tasks = viewModel.taskList.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsState()

    var showMenuCreate by rememberSaveable {
        mutableStateOf(false)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Red)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Todo App",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
        )

        EditSpacer()

        LazyColumn(modifier = Modifier.weight(4f)) {

            items(
                count = tasks.itemCount,
                key = tasks.itemKey { it.taskId },
                contentType = tasks.itemContentType { "Tasks" }
            ) {
                val task = tasks[it]

                if (task != null) {
                    TaskItem(taskItem = task, context = context, viewModel = viewModel) { bool ->
                        viewModel.editTask(
                            taskModel = task.copy(
                                isCompleted = bool,
                                completedDate = System.currentTimeMillis()
                            )
                        )
                    }


                }
                EditSpacer()
            }
        }

        MyFabAddButton() { showMenuCreate = true }

    }
    if (showMenuCreate) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            DialogCreateTask(
                title = stringResource(id = R.string.strAddTask),
                textFieldValue = "",
                buttonText = stringResource(id = R.string.strAdd),
                closeDialog = {
                    showMenuCreate = false
                },
                actionFunc = {
                    val currentTimeInMillis: Long = System.currentTimeMillis()
                    viewModel.createTask(
                        TaskModel(
                            taskId = 0,
                            taskName = it,
                            creationDate = currentTimeInMillis,
                            isCompleted = false,
                            completedDate = null
                        )
                    )
                }
            )
        }
    }

    state.message?.let {
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        viewModel.resetMessages()
    }
}

// Composable creado para el diseño de cada una de las tareas a mostrar, además de agregar sus funciones
@Composable
fun TaskItem(
    taskItem: TaskModel,
    viewModel: TaskViewModel,
    context: Context,
    onCheckedChangeValue: (Boolean) -> Unit
) {
    var isCompleted by rememberSaveable {
        mutableStateOf(taskItem.isCompleted)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    var showFullTask by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Checkbox(
                checked = isCompleted, onCheckedChange = {
                    isCompleted = !isCompleted
                    onCheckedChangeValue(isCompleted)

                    // Reproducción de sonido al completar una tarea
                    SoundPlayer.playSound(context)
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .border(border = BorderStroke(1.dp, color = Color.White), shape = CircleShape)
                    .size(30.dp)
                    .background(Color.White),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    uncheckedColor = Color.Transparent,
                    checkmarkColor = Color.Black,
                )
            )

            Text(
                text = taskItem.taskName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clickable {
                        showFullTask = true
                    }
            )

            DropDownMenuTask(openDialog = {
                showDeleteDialog = true
            },
                openEdit = {
                    showEditDialog = true
                }
            )

            // Muestra un diálogo que te muestra el nombre completo de la tarea en caso de que sea muy larga
            if (showFullTask) {
                AlertDialog(onDismissRequest = {}, confirmButton = {
                    TextButton(onClick = { showFullTask = false }) {
                        Text(
                            text = "OK",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                    title = {
                        Text(
                            text = stringResource(id = R.string.strTask),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    text = {
                        Text(text = taskItem.taskName, fontSize = 14.sp)
                    })
            }

            //Muestra el diálogo que te permite editar una tarea
            if (showEditDialog) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    DialogCreateTask(
                        title = stringResource(id = R.string.strEditTask),
                        buttonText = stringResource(id = R.string.strUpdate),
                        textFieldValue = taskItem.taskName,
                        closeDialog = {
                            showEditDialog = false
                        },
                        actionFunc = {
                            viewModel.editTask(
                                taskItem.copy(
                                    taskName = it
                                )
                            )
                        })
                }

            }

            // Muestra el diálogo para eliminar una tarea

            if (showDeleteDialog) {
                DeleteDialog(
                    deleteTask = {
                        viewModel.deleteTask(taskItem)
                    },
                    closeDialog = {
                        showDeleteDialog = false
                    }
                )
            }

        }
    }
}

@Composable
fun MyFabAddButton(showMenuCreate: () -> Unit) {
    IconButton(
        onClick = {
            showMenuCreate()
        },
        modifier = Modifier.size(65.dp),
        colors = IconButtonDefaults.iconButtonColors(containerColor = buttonColor)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Button to add a task")
    }
}


@Composable
fun DropDownMenuTask(openDialog: () -> Unit, openEdit: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box {
        IconButton(onClick = {
            expanded = !expanded
        }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Icon called three points, it is menu of the task",
                modifier = Modifier.size(30.dp)
            )
        }

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier.align(
                Alignment.CenterEnd
            )
        ) {
            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = R.string.strEdit),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }, onClick = {
                openEdit()
            })

            HorizontalDivider()

            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = R.string.strDelete),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal
                )
            }, onClick = {
                openDialog()
            })
        }
    }
}

// Diálogo realizado la creacion de nuevas tareas
@Composable
fun DialogCreateTask(
    title: String,
    buttonText: String,
    textFieldValue: String,
    closeDialog: () -> Unit,
    actionFunc: (String) -> Unit
) {

    var textValue by remember {
        mutableStateOf(textFieldValue)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(bgColor)
                .padding(vertical = 20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                    closeDialog()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Close Button",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }

            EditSpacer()

            Text(text = title, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)

            EditSpacer()

            TextField(
                value = textValue, onValueChange = { textValue = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 10.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            EditSpacer()

            Button(
                modifier = Modifier.padding(10.dp),
                onClick = {
                    actionFunc(textValue)
                    closeDialog()
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            ) {
                Text(
                    text = buttonText,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
            }
        }
    }
}

// Diálogo creado para la eliminación de tareas

@Composable
fun DeleteDialog(closeDialog: () -> Unit, deleteTask: () -> Unit) {
    AlertDialog(onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {
                deleteTask()
                closeDialog()
            }) {
                Text(
                    text = stringResource(id = R.string.strDelete),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }, dismissButton = {
            TextButton(onClick = {
                closeDialog()
            }) {
                Text(
                    text = stringResource(id = R.string.strCancel),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.strDeleteTask),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        },
        text = {
            Text(text = stringResource(id = R.string.strWouldYouDeleteTask), fontSize = 18.sp)
        })
}

@Composable
fun EditSpacer() {
    Spacer(modifier = Modifier.size(23.dp))
}