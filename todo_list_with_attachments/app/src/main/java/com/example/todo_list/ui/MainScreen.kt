package com.example.todo_list.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.todo_list.R
import com.example.todo_list.ui.task.TaskList
import com.example.todo_list.ui.view.TaskViewModel

@Composable
fun MainScreen(
    viewModel: TaskViewModel,
    navController: NavController,
    onAddTaskClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Todo List") },
                actions = {
                    IconButton(onClick = {
                        if (viewModel.sortBy.value) {
                            viewModel.sortByPriorityLow()
                        } else {
                            viewModel.sortByPriorityHigh()
                        }
                        viewModel.sortBy.value = !(viewModel.sortBy.value)
                        navController.navigate("main_screen")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Sort,
                            contentDescription = stringResource(R.string.sort))
                    }
                    IconButton(onClick = { navController.navigate("settings_screen") }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("search_screen") }) {
                        Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.search))
                    }
                },
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        TaskList(
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier.padding(it))
    }
}
