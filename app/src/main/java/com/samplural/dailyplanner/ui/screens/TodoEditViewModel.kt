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

class TodoEditViewModel(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    var title: String = ""

    suspend fun getTodoById(id: Int): String {
        return todoRepository.getTodoById(id).title
    }


    val todoEditUiState: StateFlow<TodoEditUiState> =
        todoRepository.getTodoStream(id = todoId).map { TodoEditUiState(it?.id) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TodoEditUiState.TIMEOUT_MILLIS),
                initialValue = TodoEditUiState()
            )

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.insertTodo(todo)
        }
    }
    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
        }
    }




}

data class TodoEditUiState(
    var id: Int? = -1,
    var title: String = "",
    var time: String = "",
    var date: String = ""
) {
    companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}

