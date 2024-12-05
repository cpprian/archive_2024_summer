package com.example.todo_list

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo_list.notifications.NotificationService
import com.example.todo_list.ui.task.AboutTaskScreen
import com.example.todo_list.ui.task.AddTaskScreen
import com.example.todo_list.ui.task.EditTaskScreen
import com.example.todo_list.ui.MainScreen
import com.example.todo_list.ui.search.SearchScreen
import com.example.todo_list.ui.theme.Todo_listTheme
import com.example.todo_list.ui.view.TaskViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<TaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContent {
            Todo_listTheme{
                TodoListApp(viewModel = viewModel)
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.CHANNEL_ID,
            "TodoList",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for the increment counter notifications"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @Composable
    fun TodoListApp(viewModel: TaskViewModel) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "main_screen") {
            composable("main_screen") {
                MainScreen(
                    viewModel = viewModel,
                    navController = navController,
                    onAddTaskClick = {
                        navController.navigate("add_task_screen")
                    }
                )
            }
            composable("add_task_screen") {
                AddTaskScreen(
                    viewModel = viewModel,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "edit_task_screen/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                EditTaskScreen(
                    viewModel = viewModel,
                    id = taskId,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "about_task_screen/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
                AboutTaskScreen(
                    viewModel = viewModel,
                    id = taskId,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = "settings_screen") {
                SettingsScreen(
                    viewModel = viewModel,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = "search_screen") {
                SearchScreen(
                    viewModel = viewModel,
                    onNavigateUp = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
