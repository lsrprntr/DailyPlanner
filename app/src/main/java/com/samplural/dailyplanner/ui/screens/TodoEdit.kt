@file:OptIn(ExperimentalMaterial3Api::class)

package com.samplural.dailyplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samplural.dailyplanner.R
import com.samplural.dailyplanner.ui.AppViewModelProvider
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    modifier: Modifier = Modifier,
    todoId: Int,
    viewModel: TodoEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClick: () -> Unit
) {
    val todoUiState by viewModel.todoEditUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


    // Initial timeState default
    val (hour, minute) = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).split(":")
    val timeState = remember { mutableStateOf(
            TimePickerState(
                initialHour = hour.toInt(),
                initialMinute = minute.toInt(),
                is24Hour = false
            )
        )
    }

    val title: MutableState<String> = if (todoId != null) {
        remember { mutableStateOf( "") }
    } else {
        remember { mutableStateOf("")}
    }

    val date = remember { mutableStateOf(LocalDate.now().toString())}

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.TopAppBarTitleEditScreen),
                        modifier = Modifier.padding(48.dp))
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(todoId.toString())
            Text(todoUiState.time + " " + todoUiState.date + " " + todoUiState.title)
            Text(timeState.value.hour.toString() + ":" + timeState.value.minute.toString())
            OutlinedTextField(
                value = title.value,
                label = {
                    Text(
                        text = "Write a task here"
                    )
                },
                onValueChange = {
                    title.value = it
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TimePicker(
                modifier = modifier.padding(top = 8.dp),
                state = timeState.value
            )

            EditDatePicker(date = date)

            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {
                        onBackClick()
                    },
                    modifier = modifier,
                ) {
                    Text(
                        text = "Cancel",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Button(
                    onClick = {

                    },
                    modifier = modifier
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun EditDatePicker(
    modifier: Modifier = Modifier,
    date: MutableState<String>
) {

    val isOpen = remember { mutableStateOf(false)}

    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            readOnly = true,
            value = date.value,
            label = { Text("Date") },
            onValueChange = {
                date.value = it
            })

        IconButton(
            onClick = { isOpen.value = true }
        ) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calendar")
        }
    }

    if (isOpen.value) {
        EditDatePickerDialog(
            onAccept = {
                isOpen.value = false
                if (it != null) {
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .toString()
                }
            },
            onCancel = {
                isOpen.value = false
            }
        )
    }
}

@Composable
fun EditDatePickerDialog(
    modifier: Modifier = Modifier,
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}


