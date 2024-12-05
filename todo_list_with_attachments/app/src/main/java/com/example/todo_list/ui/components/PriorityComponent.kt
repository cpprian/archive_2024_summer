package com.example.todo_list.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo_list.constants.Priority

@Composable
fun PriorityComponent(priority: Priority, onPrioritySelected: (Priority) -> Unit) {
    Row {
        PriorityButton(
            text = "LOW",
            isSelected = priority == Priority.LOW,
            onClick = { onPrioritySelected(Priority.LOW) }
        )
        PriorityButton(
            text = "MEDIUM",
            isSelected = priority == Priority.MEDIUM,
            onClick = { onPrioritySelected(Priority.MEDIUM) }
        )
        PriorityButton(
            text = "HIGH",
            isSelected = priority == Priority.HIGH,
            onClick = { onPrioritySelected(Priority.HIGH) }
        )
    }
}

@Composable
private fun PriorityButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val contentColor = if (isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(text = text)
    }
}