package com.example.todo_list.ui.task

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todo_list.constants.toColor
import com.example.todo_list.constants.toPriority
import com.example.todo_list.db.Task
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskItem(
    task: Task,
    navController: NavController,
    onDeleteClick: () -> Unit,
    onTaskCheckChanged: (Task, Boolean) -> Unit
) {
    val context = LocalContext.current
    val createdAt by remember { mutableStateOf(task.createdAt) }
    val doneAt by remember { mutableStateOf(task.doneAt) }
    val dateDoneAt = Date(doneAt)
    val dateCreatedAt = Date(createdAt)
    val dateFormat = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
    val formattedDoneAt = dateFormat.format(dateDoneAt)
    val formattedCreatedAt = dateFormat.format(dateCreatedAt)

    Box(
        modifier = Modifier
            .clickable {
                navController.navigate("about_task_screen/${task.id}")
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { isChecked ->
                                onTaskCheckChanged(task, isChecked)
                                Toast.makeText(
                                    context,
                                    "Task ${task.title} is ${if (isChecked) "completed" else "not completed"}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .background(
                                    color = toPriority(task.priority).toColor(),
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                        Text(
                            text = task.title,
                            modifier = Modifier
                                .padding(end = 15.dp),
                            style = MaterialTheme.typography.h6,
                            color = if (task.isCompleted) Color.Gray else Color.Black
                        )
                    }
                    if (task.attachment != null) {
                        Icon(
                            imageVector = Icons.Filled.Attachment,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = onDeleteClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate("edit_task_screen/${task.id}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Created at",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                            color = if (task.isCompleted) Color.Gray else Color.Black
                        )
                        Text(
                            text = formattedCreatedAt,
                            style = MaterialTheme.typography.body2,
                            color = if (task.isCompleted) Color.Gray else Color.Black
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Deadline at",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Bold,
                            color = if (task.isCompleted) Color.Gray else Color.Black
                        )
                        Text(
                            text = formattedDoneAt,
                            style = MaterialTheme.typography.body2,
                            color = if (task.isCompleted) Color.Gray else Color.Black
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.tag,
                        modifier = Modifier
                            .weight(0.25f)
                            .padding(end = 8.dp),
                        style = MaterialTheme.typography.body1,
                        color = if (task.isCompleted) Color.Gray else Color.Black
                    )
                    Text(
                        text = if (task.description.length > 30) task.description.slice(0..25) + "..." else task.description,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        style = MaterialTheme.typography.body1,
                        color = if (task.isCompleted) Color.Gray else Color.Black
                    )
                }
            }
        }
    }
}
