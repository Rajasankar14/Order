package com.online.order.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.online.order.R
import com.online.order.ui.components.AppIcon
import com.online.order.ui.components.AppSmallTextView

@Composable
fun NotificationScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .safeContentPadding()) {
        AppIcon(imageVector = Icons.Default.ArrowBack, tintColor = MaterialTheme.colorScheme.onBackground, modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .size(30.dp)
            .clickable {
                navController.navigateUp()
            })

        Column(modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {




            Image(
                painter = painterResource(id = R.drawable.ic_empty_data),
                contentScale = ContentScale.Crop,
                contentDescription = "jj",
                modifier = Modifier
                    .size(150.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)


                )
            AppSmallTextView(
                text = "No Data found",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )


        }
    }

}