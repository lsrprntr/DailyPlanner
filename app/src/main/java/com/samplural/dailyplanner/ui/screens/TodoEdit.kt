package com.samplural.dailyplanner.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoEdit(
    todoItem: Any,
    onBackClicked: () -> Boolean,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onBackClicked() },
        modifier = modifier
    ){
        Text(
            text = "Back",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun TodoEditPreview() {
    TodoEdit(
        todoItem = "",
        onBackClicked = { false },
    )
}