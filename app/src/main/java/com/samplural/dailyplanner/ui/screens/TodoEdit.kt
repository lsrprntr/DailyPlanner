@file:OptIn(ExperimentalMaterial3Api::class)

package com.samplural.dailyplanner.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.DatePickerState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samplural.dailyplanner.R
import com.samplural.dailyplanner.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    modifier: Modifier = Modifier,
    todoId: Int = 0,
    viewModel: TodoEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClick: () -> Unit
) {
    val todoUiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var todo by remember { mutableStateOf(null) }

    val timeAndDate = System.currentTimeMillis()
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val (hour, minute) = formatter.format(Date(timeAndDate)).split(":")

    val timeState = TimePickerState(
        initialHour = hour.toInt(),
        initialMinute = minute.toInt(),
        is24Hour = false
    )
    val dateState = DatePickerState(
        locale = Locale.getDefault(),
        initialSelectedDateMillis = timeAndDate,
    )

    val time = formatter.format(Date(timeAndDate))


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
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text("$todoId", modifier = Modifier.padding(paddingValues))
            Text(time)

            OutlinedTextField(
                value = "todoUiState",

                label = {
                    Text(
                        text = "Write a task here"
                    )
                },
                onValueChange = {
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TimePicker(
                modifier = modifier.padding(top = 8.dp),
                state = timeState
            )

            CustomDatePicker()

            Button(
                onClick = {
                    viewModel.insertTodo(todo = todo)
                    onBackClick()
                },
                modifier = modifier
            ) {
                Text(
                    text = "Back/Save",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CustomDatePicker() {
    val date = remember { mutableStateOf(LocalDate.now())}
    val isOpen = remember { mutableStateOf(false)}

    Row(verticalAlignment = Alignment.CenterVertically) {

        OutlinedTextField(
            readOnly = true,
            value = date.value.format(DateTimeFormatter.ISO_DATE),
            label = { Text("Date") },
            onValueChange = {})

        IconButton(
            onClick = { isOpen.value = true }
        ) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calendar")
        }
    }

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // close dialog

                if (it != null) { // Set the date
                    date.value = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                }
            },
            onCancel = {
                isOpen.value = false //close dialog
            }
        )
    }
}

@Composable
fun CustomDatePickerDialog(
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
@Composable
fun DatePickerExample(
    modifier: Modifier = Modifier,
    dateState: DatePickerState,
) {
    // Initial date
    var selectedDate by remember { mutableStateOf(dateState) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val time = selectedDate

    Row() {
        // Display the selected date
        Text(
            text = "Selected Date: ${selectedDate.selectedDateMillis}",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Button to open the date picker dialog
        Button(onClick = { showDatePickerDialog = true }) {
            Text(text = "Select Date")
        }
    }

    // Date picker dialog
    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showDatePickerDialog = false
                        // Update the selected date
                        selectedDate = selectedDate
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePickerDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            content = {
                DatePicker(
                    state = selectedDate,
                    )
            }
        )
    }
}


