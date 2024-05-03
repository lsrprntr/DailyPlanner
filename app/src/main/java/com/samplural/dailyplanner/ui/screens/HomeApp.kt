package com.samplural.dailyplanner.ui.screens

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samplural.dailyplanner.R


/**
 * enum values that represent the screens in the app
 */
enum class TodoScreen(@StringRes val title: Int) {
    TodoListScreen(title = R.string.todo_list_screen),
    EditTodoScreen(title = R.string.edit_todo),
}

// Start of Navigation Tree
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TodoScreen.TodoListScreen.name,
        modifier = modifier
    ) {
        composable(route = TodoScreen.TodoListScreen.name) {
            DailyPlannerScreen()
        }
        composable(route = TodoScreen.EditTodoScreen.name) {

        }
    }
}






