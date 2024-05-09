package com.samplural.dailyplanner.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TodoScreen.TodoListScreen.name,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(route = TodoScreen.TodoListScreen.name) {
                TodoListScreen(
                    onTodoClick = { todo ->
                        navController.navigate(route = "${TodoScreen.EditTodoScreen.name}/${todo.id}")
                                  },
                    onAddTodoClick = {
                        navController.navigate(route = "${TodoScreen.EditTodoScreen.name}/-1")
                    }
                )
            }
            composable(
                route = TodoScreen.EditTodoScreen.name + "/{todoId}",
                arguments = listOf(
                    navArgument("todoId") { type = NavType.IntType }
                )
            ) {
                    backStackEntry ->
                val todoId = backStackEntry.arguments?.getInt("todoId")

                if (todoId != null) {
                    TodoEditScreen(
                        todoId = todoId,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

            }
        }
    }

}






