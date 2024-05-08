package com.samplural.dailyplanner.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samplural.dailyplanner.data.Todo
import com.samplural.dailyplanner.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodoEditViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    //Observable - pass events here
    private val _uiState = MutableStateFlow(TodoEditUiState())
    //State is
    val uiState: StateFlow<TodoEditUiState> = _uiState.asStateFlow()



    fun updateTitle(title: String){
        _uiState.update {
            it.copy(title = title)
        }
    }
    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
        }
    }

    val timeAndDate = LocalDate.now()
    val day = timeAndDate.dayOfMonth
    val month = timeAndDate.monthValue
    val year = timeAndDate.year
}

data class TodoEditUiState(
    val id: Int? = null,
    val title: String = "",
    val timeAndDate: LocalDate = LocalDate.now(),
)