package com.example.todo_list.ui.task

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todo_list.R
import com.example.todo_list.ui.components.DateTimePicker
import com.example.todo_list.ui.components.PriorityComponent
import com.example.todo_list.constants.toInt
import com.example.todo_list.constants.toPriority
import com.example.todo_list.ui.view.TaskViewModel
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTaskScreen(
    id: Int,
    viewModel: TaskViewModel,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    val task = viewModel.getSelectedTask(id).observeAsState().value
    if (task?.title == null) {
        return
    }
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var priority by remember { mutableStateOf(toPriority(task.priority)) }
    var tag by remember { mutableStateOf(task.tag) }
    var isNotified by remember { mutableStateOf(task.isNotified) }
    val selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val selectedTime by remember { mutableStateOf(Calendar.getInstance()) }
    var attachment by remember { mutableStateOf(task.attachment) }

    val pickFile = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            attachment = inputStream?.readBytes()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Task",
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = stringResource(R.string.title_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        PriorityComponent(
            priority = priority,
            onPrioritySelected = {
                priority = it
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = stringResource(R.string.description_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = tag,
            onValueChange = { tag = it },
            label = { Text(text = stringResource(R.string.category_task)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateTimePicker(
            doneAt = task.doneAt,
            selectedDate = selectedDate,
            selectedTime = selectedTime)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isNotified,
                onCheckedChange = { isNotified = it })
            Text(
                text = stringResource(R.string.notify_task),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AttachFile,
                contentDescription = "Attach file",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { pickFile.launch("*/*") },
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            attachment?.let {
                Text(
                    text = "Attached file (${it.size} bytes)",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedTask = task.copy(
                    title = title,
                    description = description,
                    priority = priority.toInt(),
                    doneAt = selectedDate.timeInMillis + selectedTime.timeInMillis - Calendar.getInstance().timeInMillis,
                    tag = tag,
                    isNotified = isNotified
                )
                viewModel.insertTask(updatedTask)
                onNavigateUp()
            }
        ) {
            Text(text = "Save")
        }
    }
}
