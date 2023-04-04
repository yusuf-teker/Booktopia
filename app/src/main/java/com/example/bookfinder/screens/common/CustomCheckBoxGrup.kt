package com.example.bookfinder.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckboxGroup(
    selectedBox: Int,
    onBoxSelected: (Int) -> Unit,
    options: List<String>
) {
    Row(modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly) {
        options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedBox == index,
                    onCheckedChange = { onBoxSelected(index)},
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
    }


}