package com.example.todo_list.ui.view

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_list.constants.Priority
import com.example.todo_list.db.Task
import com.example.todo_list.db.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = TaskDatabase.getInstance(application).taskDao()
    var tasks: LiveData<List<Task>> = taskDao.getAll().asLiveData()
    var priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    var sortBy: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var onlyUncompleted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var sortOption: MutableStateFlow<String> = MutableStateFlow("")
    var notificationLength: MutableStateFlow<Int> = MutableStateFlow(0)
    var searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    fun getTasks() {
        tasks = taskDao.getAll().asLiveData()
    }

    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(taskId)
        }
    }

    fun getSelectedTask(taskId: Int): LiveData<Task> {
        return requireNotNull(taskDao.getSelectedTask(taskId).asLiveData())
    }

    fun sortByPriorityLow() {
        tasks = taskDao.sortByPriorityLow().asLiveData()
    }

    fun sortByPriorityHigh() {
        tasks = taskDao.sortByPriorityHigh().asLiveData()
    }

    fun searchDatabase(searchQuery: String) {
        tasks = taskDao.searchDatabase(searchQuery).asLiveData()
    }

    fun getUncompletedTasks() {
        tasks = taskDao.getUncompletedTasks().asLiveData()
    }

    fun getTasksByTag(tag: String) {
        tasks = taskDao.getTasksByTag(tag).asLiveData()
    }

    fun getTags(): LiveData<List<String>> {
        return taskDao.getTags().asLiveData()
    }

    fun checkTask(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.checkTask(taskId, isCompleted)
        }
    }

    fun openAttachment(attachment: ByteArray, context: Context) {
        val tempFile = File.createTempFile("attachment", null, context.cacheDir)
        val fos = FileOutputStream(tempFile)
        fos.write(attachment)
        fos.flush()
        fos.close()

        val fileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tempFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, "*/*")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        context.startActivity(intent)
    }
}

