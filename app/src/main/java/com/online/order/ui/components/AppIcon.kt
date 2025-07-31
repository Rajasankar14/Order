package com.online.order.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppIcon(imageVector: ImageVector,  modifier: Modifier = Modifier, tintColor: Color =Color.Black, size: Dp = 40.dp ) {
    Icon(
        imageVector = imageVector,
        contentDescription = "food",
        modifier = modifier,
        tint = tintColor
    )
}