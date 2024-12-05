package com.example.todo_list.constants

import androidx.compose.ui.graphics.Color

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}

fun Priority.toColor(): Color {
    return when (this) {
        Priority.LOW -> Color(0xFF8BC34A)
        Priority.MEDIUM -> Color(0xFFFF9D05)
        Priority.HIGH -> Color(0xFFFF5252)
    }
}

fun toPriority(priority: Int): Priority {
    return when (priority) {
        0 -> Priority.LOW
        1 -> Priority.MEDIUM
        2 -> Priority.HIGH
        else -> Priority.LOW
    }
}

fun Priority.toInt(): Int {
    return when (this) {
        Priority.LOW -> 0
        Priority.MEDIUM -> 1
        Priority.HIGH -> 2
    }
}

fun Priority.toString(): String {
    return when (this) {
        Priority.LOW -> "LOW"
        Priority.MEDIUM -> "MEDIUM"
        Priority.HIGH -> "HIGH"
    }
}