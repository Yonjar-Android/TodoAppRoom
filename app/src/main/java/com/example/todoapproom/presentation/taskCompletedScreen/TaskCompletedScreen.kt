package com.example.todoapproom.presentation.taskCompletedScreen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.todoapproom.R
import com.example.todoapproom.domain.model.TaskModel
import com.example.todoapproom.ui.theme.bgColor

@Composable
fun TaskCompletedScreen(viewModel: TasksCompletedViewModel) {

    val tasks = viewModel.taskList.collectAsLazyPagingItems()

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    if (state.isLoading) {
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
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("TaskCompletedScreen")
        )

        Spacer(modifier = Modifier.size(15.dp))

        Text(
            text = stringResource(id = R.string.strLast7Days),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {

            items(
                count = tasks.itemCount,
                key = tasks.itemKey { it.taskId },
                contentType = tasks.itemContentType{ "Tasks" }
            ){ index ->
                val task = tasks[index]
                task?.let {
                    TaskCompletedItem(task = task, onCheckedChangeValue = {
                        viewModel.updateTask(
                            taskModel = task.copy(
                                isCompleted = it,
                                completedDate = null
                            )
                        )
                    })

                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
        }
    }

    state.message?.let {
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        viewModel.resetMessages()
    }
}

@Composable
fun TaskCompletedItem(task: TaskModel, onCheckedChangeValue: (Boolean) -> Unit) {


    var isCompleted by rememberSaveable {
        mutableStateOf(task.isCompleted)
    }

    var showFullTask by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(10.dp)
            .clickable {
                showFullTask = true
            },
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
            text = task.taskName,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            textDecoration = TextDecoration.LineThrough,
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
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
                    Text(text = task.taskName, fontSize = 14.sp)
                })
        }
    }
}