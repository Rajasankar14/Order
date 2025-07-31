package com.online.order.ui.screens.home.order

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppLargeTextView
import com.online.order.ui.components.AppMediumTextView
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.theme.AppTheme
import com.online.order.theme.orangeNavItem
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel

@Composable
fun OrderSuccessScreen(navController: NavController, foodieZoneViewModel: FoodieZoneViewModel) {

    val imageScale = remember { Animatable(0f) }

    val orderIdScale = remember { Animatable(0f) }

    val alpha = remember { Animatable(1f) }


    LaunchedEffect(Unit) {
        imageScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        orderIdScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = LinearOutSlowInEasing
            )
        )
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis()-startTime < 3000) {
            alpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500)
            )
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }

        navController.navigateUp()


    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_order_confirm),
                    contentDescription = "",
                    modifier = Modifier
                        .size(140.dp)
                        .graphicsLayer(scaleX = imageScale.value, scaleY = imageScale.value)
                )
                AppLargeTextView(
                    "Your Order is confirmed",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                AppMediumTextView(
                    "Order Id is ORDFZ1111",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.graphicsLayer(
                        scaleX = orderIdScale.value,
                        scaleY = orderIdScale.value
                    )
                )
                Column(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .graphicsLayer(scaleX = orderIdScale.value, scaleY = orderIdScale.value),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

                        )
                        AppMediumTextView(
                            "Delivery at",
                            modifier = Modifier.padding(start = 6.dp),
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        AppMediumTextView(
                            "Home",
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    AppMediumTextView(
                        "2nd Floor, Kannan Apartment,west John St ,Guindy,Chennai",
                        modifier = Modifier.padding(top = 2.dp, start = 12.dp, end = 12.dp),
                        fontWeight = FontWeight.Normal, textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    AppMediumTextView(
                        "Please don't click on back button or close the app, it will automatically close",
                        modifier = Modifier.padding(top = 6.dp, start = 12.dp, end = 12.dp) .graphicsLayer { this.alpha = alpha.value },
                        fontWeight = FontWeight.Normal, textAlign = TextAlign.Center,
                        color = orangeNavItem
                    )

                }
            }
        }


    }

}

@Composable
@PreviewLightDark
fun OrderSuccessScreenPreview() {
    AppTheme {
        OrderSuccessScreen(
            rememberNavController(),
            FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository())
        )
    }

}