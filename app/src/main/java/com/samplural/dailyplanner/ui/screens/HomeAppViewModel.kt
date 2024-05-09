package com.samplural.dailyplanner.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samplural.dailyplanner.data.Todo
import com.samplural.dailyplanner.data.TodoRepository
import kotlinx.coroutines.launch

class HomeAppViewModel(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    var todo: Todo = Todo(id = 0, title = "", time = "", date = "")

    init {
        viewModelScope.launch {
        }
    }
    suspend fun loadTodo(todoId: Int): Todo {
        val todo: Todo = todoRepository.getTodoById(todoId)
        return todo
    }


}
