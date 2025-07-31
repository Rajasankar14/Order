package com.online.order.ui.screens.home.myOrder

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.repository.StoreRepository
import com.online.order.theme.AppTheme
import com.online.order.theme.darkGreen
import com.online.order.ui.screens.home.order.FakeAddressDao
import com.online.order.ui.screens.home.order.FakeMyOrderDao
import com.online.order.ui.screens.home.order.FakeOrderDetailDao
import com.online.order.ui.screens.home.order.OrderViewModel
import kotlinx.coroutines.launch


@Composable
fun MyOrders(navController: NavController, orderViewModel: OrderViewModel, isFromProfileScreen : Boolean) {


    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val myOrdersList by orderViewModel.myOrderList.collectAsState(initial = emptyList())


    LaunchedEffect(Unit) {
        orderViewModel.uiEvent.collect{ event ->
            when (event) {
                is OrderViewModel.UiEvent.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is OrderViewModel.UiEvent.NavigateToOrderConfirmation -> {}

                is OrderViewModel.UiEvent.BottomSheetState -> {


                }
            }
        }

    }
    Column(modifier = Modifier.then(if(isFromProfileScreen) Modifier.safeContentPadding().fillMaxSize() else Modifier)) {

        MyOrdersToolBar(navController)
        if (myOrdersList.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_empty),
                    contentScale = ContentScale.Crop,
                    contentDescription = "jj",
                    modifier = Modifier
                        .size(150.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)


                )
                AppSmallTextView(
                    text = "No orders found",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {


            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
            ) {
//                orderViewModel.myOrderList.forEach { orderItem ->
//                    item {
//
//
//                    }

                    items(myOrdersList){ orderItem ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {


                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)

                            ) {
                                Row {
                                    Image(
                                        painter = painterResource(id = R.drawable.bucket_briyani),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "jj",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )

                                    Column(modifier = Modifier.padding(start = 8.dp)) {
                                        AppMediumTextView(
                                            text = orderItem.orders.storeName,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.SemiBold,
                                            sp = 16.sp
                                        )
                                        AppMediumTextView(
                                            text = orderItem.orders.storeLocation,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Normal,
                                            sp = 12.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "food",
                                        modifier = Modifier
                                            .align(Alignment.Top)
                                            .size(20.dp)
                                            .clickable {
                                                coroutineScope.launch {
                                                    orderViewModel.deleteOrder(orderItem.orders.orderId)
                                                }

                                            },
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }

                            orderItem.orderDetails.forEach { orderDetailItem ->
                                Row(modifier = Modifier) {
                                    AppMediumTextView(
                                        text = orderDetailItem.foodName,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Normal,
                                        sp = 14.sp,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )

                                    AppMediumTextView(
                                        text = " x " + orderDetailItem.foodType,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Normal,
                                        sp = 14.sp,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }



                            Row {
                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                    AppSmallTextView(
                                        text = "Order placed on 7th May, 10:11PM",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )

                                    AppMediumTextView(
                                        text = if(orderItem.orders.orderStatus == 0) "Waiting for Confirmation" else "OnGoing",
                                        color = darkGreen,
                                        fontWeight = FontWeight.SemiBold,
                                        sp = 15.sp,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                AppMediumTextView(
                                    text = "₹190.25",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.SemiBold,
                                    sp = 16.sp,
                                    modifier = Modifier
                                        .padding(vertical = 2.dp)
                                        .align(Alignment.Top)
                                )
                            }
                        }

                    }
                    }
               // }
            }

        }
    }
}


@Composable
fun MyOrdersToolBar(navController: NavController) {
    Column(modifier = Modifier) {

        Row(
            modifier = Modifier
                .height(45.dp)
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "food",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(indication = null, interactionSource = remember {
                        MutableInteractionSource()
                    }) {
                        navController.navigateUp()
                    },
                tint = MaterialTheme.colorScheme.onBackground
            )
            AppMediumTextView(
                text = "My Orders",
                modifier = Modifier.padding(start = 6.dp),
                fontWeight = FontWeight.Bold,
                sp = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))




            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "food",
                modifier = Modifier
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )


        }

    }
}

@PreviewLightDark
@Composable
fun MyOrdersPreview() {
    AppTheme {
        MyOrders(rememberNavController(), OrderViewModel(StoreRepository(), FakeMyOrderDao(),
            FakeOrderDetailDao(), FakeAddressDao()
        ), false)
    }
}