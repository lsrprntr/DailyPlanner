package com.samplural.dailyplanner.data

import android.content.Context

interface AppContainer {
    val todoRepository: TodoRepository
}

// DI class taking context as dependency
class AppDataContainer(private val context: Context) : AppContainer {
    override val todoRepository: TodoRepository by lazy {
        OfflineTodoRepository(TodoDatabase.getDatabase(context).todoDao())
    }
}