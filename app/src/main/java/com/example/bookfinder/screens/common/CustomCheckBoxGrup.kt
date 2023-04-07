package com.example.bookfinder.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckboxGroup(
    selectedBox: Int,
    onBoxSelected: (Int) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()){
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = 600.dp),
            columns = GridCells.Fixed(2)
        ) {
            
            options.forEachIndexed { index,option ->
                item(
                    index,

                    span = { GridItemSpan(
                        if (index==(options.size-1) && index%2==0) 2 else 1
                    ) }

                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Checkbox(
                            checked = selectedBox == index,
                            onCheckedChange = { onBoxSelected(index)},
                            modifier = Modifier.padding(vertical = 4.dp),
                            colors = CheckboxDefaults.colors(uncheckedColor = Color.Gray, checkedColor = Color.Green)
                        )
                        Text(
                            text = options.get(index),
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(end = 4.dp, top = 4.dp, bottom = 4.dp),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
        }
    }


}