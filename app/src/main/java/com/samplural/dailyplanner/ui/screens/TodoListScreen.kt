@file:OptIn(ExperimentalMaterial3Api::class)

package com.samplural.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.AppTypography
import com.samplural.dailyplanner.R
import com.samplural.dailyplanner.data.Todo
import com.samplural.dailyplanner.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onTodoClick: (Todo) -> Unit,
    onAddTodoClick: () -> Unit
) {
    val todoUiState by viewModel.todoListUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                        Text(
                            stringResource(R.string.TopAppBarTitleListScreen),
                            modifier = Modifier.padding(48.dp))
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTodoClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_item)
                )
            }
        },
    ) { innerPadding ->
        TodoListBody(
                todoList = todoUiState.todoList,
                onTodoClick = onTodoClick,
                modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TodoListBody(
    todoList: List<Todo>,
    onTodoClick: (Todo) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(todoList) { item ->
            TodoItem(todo = item, onTodoClick = onTodoClick)
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onTodoClick: (Todo) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = { onTodoClick(todo) }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = todo.title,
                modifier = Modifier.padding(8.dp),
                style = AppTypography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = 3,
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "12:30PM - 1:00PM 12/12/2023",
                    modifier = modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    style = AppTypography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Todo")
                }
            }

        }

    }
}


@Preview
@Composable
fun TodoListScreenPreview() {
    val todo = Todo(
        id = 1,
        title = "Learn Compose and laskdjfalsdfj lksjadfas lkjsdfa and the overflow"
    )
    TodoItem(todo = todo, onTodoClick = {})
}
