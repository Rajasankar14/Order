package com.online.order.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.navigation.AuthNavGraph
import com.online.order.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {


    LaunchedEffect(key1 = Unit) {
        delay(3000)
        navController.navigate(AuthNavGraph.HOME.route){
            popUpTo(AuthNavGraph.SPLASH.route){inclusive = true}
        }

    }


        val circles = remember {
            listOf(
                CircleData(
                    size = 400.dp,
                    color = Color(0xFF0D47A1) // Dark Blue
                ),
                CircleData(
                    size = 400.dp,
                    color = Color(0xFF4E342E) // Dark Brown
                )
            )
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (300.dp), y = (300.dp / 2))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)

        ){
            Image(
                painter = painterResource(id = R.drawable.ic_cake),
                contentDescription = "food",
                modifier = Modifier
                    .size(50.dp).align(Alignment.Center),

                )
        }


        Box(
            modifier = Modifier
                .size(circles[1].size)
                .offset(x = (circles[1].size / 2), y = (circles[1].size / 2))
                .clip(HalfCircleShape(HalfCircleType.BOTTOM_RIGHT))
                .background(circles[1].color)
                .align(Alignment.BottomEnd),

            )

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-80.dp), y = (100.dp / 4))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)

        ){
            Image(
                painter = painterResource(id = R.drawable.ic_icecream),
                contentDescription = "food",
                modifier = Modifier
                    .size(50.dp).align(Alignment.Center),

                )
        }


        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-30.dp), y = (700.dp))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)

        ){
            Image(
                painter = painterResource(id = R.drawable.ic_burger),
                contentDescription = "food",
                modifier = Modifier
                    .size(50.dp).align(Alignment.Center),

            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_delivery),
            contentDescription = "delivery",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomEnd)
                .offset(
                    x = (-circles[1].size / 4),
                    y = (-circles[1].size / 2)
                ),
        )



        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_food),
                contentDescription = "food",
                modifier = Modifier
                    .size(70.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = "Foodie Zone",
                modifier = Modifier,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Let's Order FOOD",
                modifier = Modifier,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.secondary
            )

        }

    }
}

enum class HalfCircleType {
    TOP_LEFT, BOTTOM_RIGHT
}

class HalfCircleShape(private val halfType: HalfCircleType) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        when (halfType) {
            HalfCircleType.TOP_LEFT -> {
                path.moveTo(0f, size.height)
                path.arcTo(
                    rect = Rect(0f, 0f, size.width, size.height * 2),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )

                path.close()
            }


            HalfCircleType.BOTTOM_RIGHT -> {
                path.moveTo(0f, 0f)
                path.arcTo(
                    rect = Rect(0f, -size.height, size.width, size.height),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )

                path.close()
            }
        }
        return Outline.Generic(path)
    }
}


@Composable
fun CircleView(size: Dp, color: Color) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}


data class CircleData(
    val size: Dp,
    val color: Color
)


@PreviewLightDark
@Preview(name = "Tablet - 10 inch", device = "spec:width=800dp,height=1280dp,dpi=240")
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(rememberNavController())
    }


}