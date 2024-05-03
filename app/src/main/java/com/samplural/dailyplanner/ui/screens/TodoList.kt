package com.samplural.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samplural.dailyplanner.data.Todo


@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    onTodoClicked: () -> Unit,
    todoList: List<Todo>
) {
    if (todoList.isEmpty()) {
        Text("Empty")
    } else {
        Text(text = "dafds")

        Column (
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(

            ) {
                items(todoList) { item ->
                    Card {
                        Button(onClick = onTodoClicked) {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}