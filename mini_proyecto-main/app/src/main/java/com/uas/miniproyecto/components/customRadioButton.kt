package com.uas.miniproyecto.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomRadioButton(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = (selectedOption == "Matutino"),
            onClick = { onOptionSelected("Matutino") }
        )

        Text(
            text = "Matutino",
            modifier = Modifier.clickable {  onOptionSelected("Matutino") }
        )

        Spacer(modifier = Modifier.width(16.dp))

        RadioButton(
            selected = (selectedOption == "Vespertino"),
            onClick = { onOptionSelected("Vespertino") }
        )

        Text(
            text = "Vespertino",
            modifier = Modifier.clickable { onOptionSelected("Vespertino") }
        )
    }
}