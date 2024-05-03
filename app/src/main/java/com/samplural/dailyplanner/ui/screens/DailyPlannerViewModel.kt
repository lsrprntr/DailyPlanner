package com.samplural.dailyplanner.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samplural.dailyplanner.data.Todo
import com.samplural.dailyplanner.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DailyPlannerViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    val dailyPlannerUiState: StateFlow<DailyPlannerUiState> =
        todoRepository.getAllTodosStream().map { DailyPlannerUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DailyPlannerUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun addTodo() {
        viewModelScope.launch {
            todoRepository.insertTodo(dailyPlannerUiState.value.todoList.first())
        }
    }
}


data class DailyPlannerUiState(
    val todoList: List<Todo> = listOf(
        Todo(1, "Wash the dishes")
    )
)
