package com.simposio.mini_proyecto_3.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.simposio.mini_proyecto_3.R
import com.simposio.mini_proyecto_3.util.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    date: Long,
    onDateChange: (Long) -> Unit
) {
    var showCalendar by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.date_label), style = MaterialTheme.typography.labelMedium)
            Text(text = DateUtils.format(date), style = MaterialTheme.typography.bodyLarge)
        }

        TextButton(onClick = { showCalendar = true }) {
            Text(stringResource(R.string.date_change))
        }
    }

    if (showCalendar) {
        val state = rememberDatePickerState(initialSelectedDateMillis = DateUtils.localToUtc(date))

        DatePickerDialog(
            onDismissRequest = { showCalendar = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        state.selectedDateMillis?.let { onDateChange(DateUtils.utcToLocal(it)) }
                        showCalendar = false
                    }
                ) { Text(stringResource(R.string.accept)) }
            },
            dismissButton = {
                TextButton(onClick = { showCalendar = false }) { Text(stringResource(R.string.cancel)) }
            }
        ) { DatePicker(state = state) }
    }
}