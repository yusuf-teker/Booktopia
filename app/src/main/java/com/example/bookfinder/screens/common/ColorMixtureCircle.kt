package com.example.bookfinder.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun ColorMixtureCircle(
    circleSize: Dp,
    colors: List<Color>,
    onColorMixtureCircleClicked: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .clickable {
                onColorMixtureCircleClicked()
            }
    ) {

        val gradientBrush = Brush.sweepGradient(colors)
        Box(
            modifier = Modifier
                .size(circleSize)
                .background(brush = gradientBrush, shape = CircleShape)
        )
    }
}