package com.example.todoapproom.presentation.taskScreen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapproom.domain.model.TaskModel

@Composable
fun TaskScreen() {

    var showMenuCreate by rememberSaveable {
        mutableStateOf(false)
    }

    val taskList = listOf(
        TaskModel(123L, "Programar", isCompleted = true),
        TaskModel(124L, "Diseñar", isCompleted = false),
        TaskModel(1257L, "Documentar", isCompleted = false),
        TaskModel(12222L, "IA", isCompleted = true),
        TaskModel(12346433L, "Programar", isCompleted = true),
        TaskModel(6, "Diseñar", isCompleted = false),
        TaskModel(1253463467L, "Documentar", isCompleted = false),
        TaskModel(3464363, "Programar", isCompleted = true),
        TaskModel(436436, "Diseñar", isCompleted = false),
        TaskModel(46346, "Documentar", isCompleted = false),
        TaskModel(1234634222L, "IA", isCompleted = true)
    )
    if (!showMenuCreate) {
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
                items(taskList, key = { it.taskId }) {
                    TaskItem(taskItem = it) { bool -> it.isCompleted = bool }
                    EditSpacer()
                }
            }

                MyFabAddButton() { showMenuCreate = true }
        }
    } else {
        DialogCreateTask() {
            showMenuCreate = false
        }
    }


}

@Composable
fun TaskItem(taskItem: TaskModel, onCheckedChangeValue: (Boolean) -> Unit) {
    var isCompleted by rememberSaveable {
        mutableStateOf(taskItem.isCompleted)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFFA5E7FF))
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
                textAlign = TextAlign.Center
            )

            DropDownMenuTask()

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
        colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0XFFD3FFA6))
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Button to add a task")
    }
}

@Composable
fun DropDownMenuTask() {

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

            })
        }
    }
}

@Composable
fun DialogCreateTask(closeDialog: () -> Unit) {

    var textValue by remember {
        mutableStateOf("")
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
            modifier = Modifier.background(Color(0XFFA5E7FF))
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

            Text(text = "Crear tarea", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)

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
                    unfocusedIndicatorColor = Color.Transparent))

            EditSpacer()

            Button(
                modifier = Modifier.padding(10.dp), onClick = {

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFD3FFA6))
            ) {
                Text(
                    text = "Crear",
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
fun EditSpacer() {
    Spacer(modifier = Modifier.size(23.dp))
}