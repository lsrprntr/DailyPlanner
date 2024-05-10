package com.samplural.dailyplanner.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.samplural.dailyplanner.TodoApplication
import com.samplural.dailyplanner.ui.screens.HomeAppViewModel
import com.samplural.dailyplanner.ui.screens.TodoEditViewModel
import com.samplural.dailyplanner.ui.screens.TodoListViewModel


/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoListViewModel(
                todoApplication().container.todoRepository
            )
        }
        initializer {
            TodoEditViewModel(
                todoApplication().container.todoRepository,
                this.createSavedStateHandle()
            )
        }
        initializer {
            HomeAppViewModel(
                todoApplication().container.todoRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TodoApplication].
 */
fun CreationExtras.todoApplication(): TodoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TodoApplication)