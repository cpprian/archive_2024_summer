package com.example.todo_list.db

import androidx.room.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val doneAt: Long = 0,
    val isNotified: Boolean = false,
    val tag: String = "None",
    val priority: Int = 0,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val attachment: ByteArray? = null,
)
