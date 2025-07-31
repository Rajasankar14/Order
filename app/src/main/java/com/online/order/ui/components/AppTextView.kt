package com.online.order.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AppSmallTextView(text : String = "Test", color: Color =Color.Black, modifier: Modifier= Modifier, fontSize :TextUnit = 12.sp, textAlign: TextAlign = TextAlign.Start, fontWeight: FontWeight = FontWeight.Normal,
                     maxlines : Int = 1, overflow : TextOverflow = TextOverflow.Ellipsis
,lineHeight : TextUnit = 14.sp) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        fontSize = fontSize,
        textAlign = textAlign,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        maxLines = maxlines,
        overflow = overflow
    )
}

@Composable
fun AppMediumTextView(text : String = "Test", color: Color = MaterialTheme.colorScheme.onBackground, modifier: Modifier= Modifier, sp :TextUnit = 14.sp, fontWeight: FontWeight = FontWeight.Normal, textAlign: TextAlign = TextAlign.Start, lineHeight :TextUnit = 14.sp) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        fontSize = sp,
        fontWeight = fontWeight,
        maxLines = 2,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun AppLargeTextView(text : String = "Test", color: Color =Color.Black, modifier: Modifier= Modifier, sp :TextUnit = 22.sp, fontWeight: FontWeight = FontWeight.SemiBold) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        fontSize = sp,
        fontWeight = fontWeight,
        maxLines = 2
    )
}