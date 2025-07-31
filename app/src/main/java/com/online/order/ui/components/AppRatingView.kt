package com.online.order.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.scenecore.ExrImage
import com.online.order.R
import com.online.order.theme.darkGreen
import com.online.order.theme.lightGrey

@Composable
fun AppRatingView(text : String = "rating", ratingImage: Int, modifier: Modifier = Modifier, color: Color = Color.White, tintColor: Color = Color.White ) {
    Row {
        AppSmallTextView(
            text = text,
            modifier = modifier,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = color
        )
        Icon(
            painter = painterResource(id = ratingImage),
            contentDescription = "star",
            modifier = Modifier
                .padding(start = 3.dp, end = 3.dp)
                .size(8.dp)
                .align(Alignment.CenterVertically),
            tint = Color.White
        )
    }
}


@Composable
fun LargeAppRatingView(text : String = "rating", ratingImage: Int, modifier: Modifier = Modifier, color: Color = Color.White, tintColor: Color = Color.White ) {
    Row {
        AppMediumTextView(
            text = text,
            modifier = modifier,
            sp = 12.sp,
            fontWeight = FontWeight.Normal,
            color = color
        )
        Icon(
            painter = painterResource(id = ratingImage),
            contentDescription = "star",
            modifier = Modifier
                .padding(start = 3.dp, end = 3.dp)
                .size(8.dp)
                .align(Alignment.CenterVertically),
            tint = Color.White
        )
    }
}