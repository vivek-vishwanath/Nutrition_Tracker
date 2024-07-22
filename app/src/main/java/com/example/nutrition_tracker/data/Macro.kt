package com.example.nutrition_tracker.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Macro(val name: String, val amount: Double, val color: Color,
                 val startPadding: Dp = 0.dp,
                 val endPadding: Dp = 0.dp,
                 val topLeft: Dp = 0.dp, val topRight: Dp = 0.dp,
                 val bottomLeft: Dp = 0.dp, val bottomRight: Dp = 0.dp
)