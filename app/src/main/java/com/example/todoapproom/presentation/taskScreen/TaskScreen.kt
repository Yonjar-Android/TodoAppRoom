package com.example.todoapproom.presentation.taskScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.ui.theme.bgColor
import com.example.todoapproom.ui.theme.buttonColor

@Composable
fun TaskScreen(viewModel: TaskViewModel) {

    val context = LocalContext.current

    val tasks by viewModel.taskList.collectAsState()
    val state by viewModel.state.collectAsState()

    var showMenuCreate by rememberSaveable {
        mutableStateOf(false)
    }

    if (state.isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Red)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
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
            items(tasks, key = { it.taskId }) {
                TaskItem(taskItem = it, viewModel = viewModel) {
                    bool ->
                    viewModel.editTask(taskModel = it.copy(
                        isCompleted = bool
                    ))
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
                title = "Crear tarea",
                textFieldValue = "",
                buttonText = "Crear",
                closeDialog = {
                    showMenuCreate = false
                },
                actionFunc = {
                    viewModel.createTask(
                        TaskModel(
                            taskId = 0,
                            taskName = it,
                            creationDate = 0,
                            isCompleted = false
                        )
                    )
                }
            )
        }
    }

    state.successMessage?.let {
        Toast.makeText(context, state.successMessage, Toast.LENGTH_SHORT).show()
        viewModel.resetMessages()
    }

    state.error?.let {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        viewModel.resetMessages()
    }

}

@Composable
fun TaskItem(taskItem: TaskModel, viewModel: TaskViewModel ,onCheckedChangeValue: (Boolean) -> Unit) {
    var isCompleted by rememberSaveable {
        mutableStateOf(taskItem.isCompleted)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var showEditDialog by remember {
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
            )

            DropDownMenuTask(openDialog = {
                showDeleteDialog = true
            },
                openEdit = {
                    showEditDialog = true
                }
            )

            if (showEditDialog) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    DialogCreateTask(
                        title = "Editar tarea",
                        buttonText = "Actualizar",
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
                    text = "Editar",
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
                    text = "Eliminar",
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

@Composable
fun DeleteDialog(closeDialog: () -> Unit, deleteTask:() -> Unit) {
    AlertDialog(onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {
                deleteTask()
                closeDialog()
            }) {
                Text(text = "Eliminar", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }, dismissButton = {
            TextButton(onClick = {
                closeDialog()
            }) {
                Text(text = "Cancelar", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        },
        title = {
            Text(
                text = "Eliminar tarea",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        },
        text = {
            Text(text = "¿Deseas eliminar la tarea?", fontSize = 18.sp)
        })
}

@Composable
fun EditSpacer() {
    Spacer(modifier = Modifier.size(23.dp))
}