package com.online.order.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppBox(modifier: Modifier = Modifier, content : @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier ){
        content()
    }
}