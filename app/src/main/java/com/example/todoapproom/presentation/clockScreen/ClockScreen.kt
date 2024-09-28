package com.example.todoapproom.presentation.clockScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp

@Composable
fun ClockScreen(){
    Text(text = "Clock Screen", fontSize = 60.sp, modifier = Modifier.testTag("ClockScreen"))

}