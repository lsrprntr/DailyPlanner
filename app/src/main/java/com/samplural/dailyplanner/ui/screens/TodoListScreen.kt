package com.samplural.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samplural.dailyplanner.R
import com.samplural.dailyplanner.data.Todo
import com.samplural.dailyplanner.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyPlannerScreen(
    modifier: Modifier = Modifier,
    viewModel: DailyPlannerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val todoUiState by viewModel.dailyPlannerUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                        Text("asfd",
                            modifier = Modifier.padding(48.dp))
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_item)
                )
            }
        },
    ) { innerPadding ->
        DailyPlannerBody(
                todoList = todoUiState.todoList,
                onTodoClick = { },
                modifier = Modifier.padding(innerPadding)
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ){
            Text(text = "testetestest",
                modifier = Modifier.padding(innerPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyPlannerBody(
    todoList: List<Todo>,
    onTodoClick: () -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
            .nestedScroll(TopAppBarDefaults.pinnedScrollBehavior().nestedScrollConnection)
    ) {
        todoList.forEach { item ->
            Card {
                Button(
                    onClick = onTodoClick,
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(48.dp)
                    )
                }
            }
        }
    }
}
