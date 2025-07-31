package com.online.order.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppLargeTextView
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.ui.components.LargeAppRatingView
import com.online.order.data.Filter
import com.online.order.data.Store
import com.online.order.data.StoreFacilities
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.navigation.AuthNavGraph
import com.online.order.theme.AppTheme
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel
import com.online.order.ui.screens.home.order.FakeAddressDao
import com.online.order.ui.screens.home.order.FakeMyOrderDao
import com.online.order.ui.screens.home.order.FakeOrderDetailDao
import com.online.order.ui.screens.home.order.OrderViewModel

@Composable
fun StoreScreen(navController: NavController, foodieZoneViewModel: FoodieZoneViewModel, orderViewModel: OrderViewModel) {
    val bgColor = MaterialTheme.colorScheme.background
    val statusBarColor = MaterialTheme.colorScheme.background


    var selectedStore = foodieZoneViewModel.getStoreDetails()
    if (selectedStore == null) {
        selectedStore = Store(
            6, "Sabarees", "Guindy", emptyList(), "1.8 km", "15-18 mins", "4.8",
            listOf(
                StoreFacilities(
                    1, "No Packing Charges", R.drawable.tc_tick
                ),
                StoreFacilities(
                    2, "Curd Rice Lovers food", R.drawable.tc_tick
                ),
                StoreFacilities(
                    3, "Pure vegeterain", R.drawable.tc_tick
                ), StoreFacilities(
                    4, "Loved by Family", R.drawable.tc_tick
                )
            ), emptyList(), emptyList(),
            true
        )
    }

   // val orderedMapList = foodieZoneViewModel.orderedMapList

    val orderedMapList by remember {
        derivedStateOf { orderViewModel.getOrderedMapListByStoreId(selectedStore.storeId) }
    }

    val totalItems by remember {
        derivedStateOf { orderViewModel.getTotalOrderedItems(orderedMapList) }
    }





    Box(modifier = Modifier
        .background(statusBarColor)
        .padding(bottom = if (orderedMapList.isNotEmpty()) 50.dp else 0.dp)) {

        Column(
            modifier = Modifier
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    bottom = if (orderedMapList.isNotEmpty()) 80.dp else 50.dp
                )
                .fillMaxHeight()
        ) {



        Column(
            modifier = Modifier
                .background(bgColor)
                .fillMaxHeight()
        ) {
            if (selectedStore != null) {
                StoreToolBar(navController = navController, selectedStore)
            }

            LazyColumn {

                item {
                    if (selectedStore != null) {
                        StoreTopView(selectedStore, foodieZoneViewModel.storeFilterList)
                    }
                }
                if (selectedStore != null) {
                    items(foodieZoneViewModel.getStoreFood(selectedStore.storeId)) { list ->

                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 6.dp)

                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                                    .padding(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(0.7f)
                                        .clickable {
                                        }

                                ) {

                                    Column(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                    ) {

                                        Row(
                                            modifier = Modifier
                                                .wrapContentWidth()

                                        ) {

                                            Image(
                                                painter = painterResource(id = R.drawable.ic_non_veg),
                                                contentDescription = "",
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))

                                            Image(
                                                painter = painterResource(id = R.drawable.ic_chilly),
                                                contentDescription = "",
                                                modifier = Modifier.size(15.dp)
                                            )
                                        }

                                        Row(
                                            modifier = Modifier.padding(top = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            AppMediumTextView(
                                                text = list.foodName,
                                                modifier = Modifier
                                                    .wrapContentWidth(),
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontWeight = FontWeight.SemiBold,
                                                sp = 15.sp
                                            )


                                        }

                                        Row(
                                            modifier = Modifier
                                                .wrapContentWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            LinearProgressIndicator(
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .height(5.dp)
                                                    .width(50.dp)
                                                    .clip(RoundedCornerShape(4.dp)),
                                                trackColor = Color.LightGray,
                                                color = Color.Green,
                                                progress = list.highlyRecommended
                                            )
                                            AppSmallTextView(
                                                text = "Higly Recommended",
                                                modifier = Modifier.padding(start = 4.dp),
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontSize = 10.sp
                                            )


                                        }

                                        Row(
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .padding(top = 4.dp)

                                        ) {


                                            AppSmallTextView(
                                                text = list.priceCurrency + list.price.toString(),
                                                modifier = Modifier,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontSize = 12.sp
                                            )


                                        }

                                        Row(
                                            modifier = Modifier
                                                .wrapContentWidth()

                                        ) {

                                            AppSmallTextView(
                                                text = list.offer,
                                                modifier = Modifier,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.tertiary,
                                                fontSize = 10.sp
                                            )
                                        }


                                    }

                                    AppSmallTextView(
                                        text = list.foodDescription,
                                        modifier = Modifier
                                            .align(Alignment.Start)
                                            .padding(top = 4.dp),
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 12.sp,
                                        maxlines = 4,
                                        lineHeight = 16.sp
                                    )

                                }

                                Spacer(modifier = Modifier.weight(0.05f))

                                Box(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .align(Alignment.CenterVertically)
                                        .height(160.dp)
                                        .width(160.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .height(140.dp)
                                            .width(160.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = list.foodImage),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "jj",
                                            modifier = Modifier
                                                .height(140.dp)
                                                .width(160.dp)


                                        )
                                    }

                                    Row(  modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .width(100.dp)
                                        .height(40.dp)
                                        .offset(y = (-10).dp)
                                        .clickable {

                                        }
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                                        , verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center) {


                                        if((orderedMapList[list.foodId]?.size?: 0) > 0){
                                            AppSmallTextView(
                                                text = "-",
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .clickable {
                                                        if (list.orderedQty > 0) {
                                                            list.orderedQty--
                                                        }
                                                        orderViewModel.removeOrderedItem(
                                                            list
                                                        )

                                                    },
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontSize = 16.sp,
                                                maxlines = 2,
                                                textAlign = TextAlign.Center,

                                                )
                                        }



                                        AppSmallTextView(
                                            text = if((orderedMapList[list.foodId]?.size?: 0) > 0) orderedMapList[list.foodId]?.size.toString() else "ADD",
                                          //  text = if(list.orderedQty > 0) list.orderedQty.toString() else "ADD",
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontSize = 16.sp,
                                            maxlines = 2,
                                            textAlign = TextAlign.Center,

                                            )

                                        AppSmallTextView(
                                            text = "+",
                                            modifier = Modifier
                                                .padding(5.dp)
                                                .clickable {
                                                    if (list.orderedQty != 0) {
                                                        list.orderedQty++
                                                    }
                                                    orderViewModel.addOrderedItem(
                                                        list
                                                    )

                                                },
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontSize = 16.sp,
                                            maxlines = 2,
                                            textAlign = TextAlign.Center,

                                            )

                                    }



                                }


                            }
                        }


                    }
                }
            }





        }
    }


        if(orderedMapList.isNotEmpty()){
            Column(modifier = Modifier
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    navController.navigate(AuthNavGraph.ORDER.route){
                        launchSingleTop = true
                    }
                }, verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                AppLargeTextView("$totalItems items Added", color = Color.White)

                AppMediumTextView("up to 50% OFF on selected items" , color = Color.White,fontWeight = FontWeight.SemiBold)

            }
        }
}

}


@Composable
fun StoreToolBar(navController: NavController,selectedStore: Store) {
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
                text = selectedStore.storeName,
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


@Composable
fun StoreTopView(selectedStore: Store,filterList : List<Filter>) {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nearby),
                contentDescription = "",
                modifier = Modifier.size(12.dp)
                , tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppMediumTextView(
                text = selectedStore.storeDistance,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppMediumTextView(
                text = " - "+selectedStore.storeLocation,
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
                    .padding(6.dp)
            ) {
                LargeAppRatingView(text = selectedStore.storeRating, ratingImage = R.drawable.ic_star_filled)
            }


        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_timer),
                contentDescription = "",
                modifier = Modifier.size(10.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppMediumTextView(
                text = "Delivery Time: 20-30 mins",
                modifier = Modifier,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

    }


        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(4.dp)
        ) {
            items(selectedStore.storeFacilities) { item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.facilityIcon),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Green
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        AppSmallTextView(
                            text = item.facilityName,
                            modifier = Modifier,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }


    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .height(50.dp)
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_offer),
            contentDescription = "",
            modifier = Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.width(4.dp))
        AppMediumTextView(
            text = "Free Delivery",
            modifier = Modifier,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1f))


        AppSmallTextView(
            text = "12 offers",
            modifier = Modifier,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "",
            modifier = Modifier.size(25.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }

    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .padding(12.dp)
    ) {
        items(filterList) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = item.filterImage),
                        contentDescription = "",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    AppSmallTextView(
                        text = item.filterName,
                        modifier = Modifier,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun StoreScreenPreview() {
    AppTheme {
        StoreScreen(
            rememberNavController(),
            FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository()),
            OrderViewModel(StoreRepository(), FakeMyOrderDao(), FakeOrderDetailDao(), FakeAddressDao())
        )
    }

}