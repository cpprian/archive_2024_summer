package com.example.todo_list.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
    return format.format(date)
}

fun Long.toDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    return format.format(date)
}

fun Long.toTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("h:mm a", Locale.getDefault())
    return format.format(date)
}