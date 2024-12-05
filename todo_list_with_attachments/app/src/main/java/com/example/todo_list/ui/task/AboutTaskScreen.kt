package com.example.todo_list.ui.task

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo_list.constants.toColor
import com.example.todo_list.constants.toPriority
import com.example.todo_list.ui.view.TaskViewModel
import com.example.todo_list.util.toDateFormat
import kotlinx.coroutines.launch

@Composable
fun AboutTaskScreen(
    id: Int,
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    val task = viewModel.getSelectedTask(id).observeAsState().value
    if (task?.title == null) {
        return
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About Task",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = task.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = task.description,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Priority: " + toPriority(task.priority).toString(),
            style = MaterialTheme.typography.h6,
            color = toPriority(task.priority).toColor()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tag: ${task.tag}",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Created at",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = task.createdAt.toDateFormat(),
                    style = MaterialTheme.typography.body2
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Done at",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = task.doneAt.toDateFormat(),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (task.attachment != null) {
            Button(
                onClick = {
                      scope.launch {
                          val attachment = task.attachment
                          viewModel.openAttachment(attachment, context)
                      }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Open attachment")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigateUp() }) {
            Text(text = "OK")
        }
    }
}