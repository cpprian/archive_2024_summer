package com.example.todo_list.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todo_list.R
import com.example.todo_list.constants.toInt
import com.example.todo_list.db.Task
import com.example.todo_list.ui.view.TaskViewModel
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    var searchQuery by remember { mutableStateOf(viewModel.searchQuery.value) }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { searchQuery = "" }) {
                Icon(Icons.Filled.Close, contentDescription = "Clear")
            }
        }
        Text(
            text = "Search Tasks",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Input search query") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.searchQuery.value = searchQuery

                if (searchQuery.isNotEmpty()) {
                    viewModel.searchDatabase(searchQuery)
                } else {
                    viewModel.getTasks()
                }
                onNavigateUp()
            }
        ) {
            Text(text = "Search")
        }
    }
}