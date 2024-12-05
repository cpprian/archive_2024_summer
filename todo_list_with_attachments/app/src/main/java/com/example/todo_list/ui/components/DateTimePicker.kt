package com.example.todo_list.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.todo_list.util.toDate
import com.example.todo_list.util.toTime
import java.util.*

@Composable
fun DateTimePicker(
    doneAt: Long,
    selectedDate: Calendar,
    selectedTime: Calendar,
) {
    val context = LocalContext.current

    var doneAtDate by remember { mutableStateOf(doneAt.toDate()) }
    var doneAtTime by remember { mutableStateOf(doneAt.toTime()) }

    Row (modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f)
        ) {
            Button(
                onClick = {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { view, year, month, dayOfMonth ->
                            selectedDate.set(Calendar.YEAR, year)
                            selectedDate.set(Calendar.MONTH, month)
                            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            doneAtDate = "${selectedDate.get(Calendar.DAY_OF_MONTH)}/" +
                                    "${selectedDate.get(Calendar.MONTH)}/${selectedDate.get(Calendar.YEAR)}"
                        },
                        selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH),
                        selectedDate.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.show()
                }
            ) {
                Text(text = "Date")
            }
            Text(
                text = doneAtDate,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Button(
                onClick = {
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { view, hourOfDay, minute ->
                            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedTime.set(Calendar.MINUTE, minute)
                            doneAtTime = "${selectedTime.get(Calendar.HOUR_OF_DAY)}:" +
                                    "${selectedTime.get(Calendar.MINUTE)}"
                        },
                        selectedTime.get(Calendar.HOUR_OF_DAY),
                        selectedTime.get(Calendar.MINUTE),
                        false
                    )
                    timePickerDialog.show()
                }
            ) {
                Text(text = "Time")
            }
            Text(
                text = doneAtTime,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
        }
    }
}