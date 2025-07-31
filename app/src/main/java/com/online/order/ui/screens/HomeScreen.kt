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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppRatingView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.BottomMenu
import com.online.order.data.Food
import com.online.order.data.FoodCategory
import com.online.order.data.Offers
import com.online.order.data.RailIOrderItem
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.database.myorderDetail.MyOrderDetail
import com.online.order.database.myorderDetail.OrderDetailDao
import com.online.order.ui.screens.home.address.AddressScreen
import com.online.order.ui.screens.home.address.AddressViewModel
import com.online.order.theme.AppTheme
import com.online.order.theme.darkGreen
import com.online.order.theme.orangeNavItem
import com.online.order.ui.screens.home.foodCategory.FoodCategoryScreen
import com.online.order.ui.screens.home.foodCategory.FoodCategoryViewModel
import com.online.order.ui.screens.home.foodiezone.FoodieZoneScreen
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel
import com.online.order.ui.screens.home.myOrder.MyOrders
import com.online.order.ui.screens.home.order.FakeAddressDao
import com.online.order.ui.screens.home.order.FakeMyOrderDao
import com.online.order.ui.screens.home.order.OrderViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(mainNavController: NavController, foodieZoneViewModel: FoodieZoneViewModel, orderViewModel: OrderViewModel) {
    var searchText by remember {
        mutableStateOf("")
    }



    var selectedIndex by remember { mutableStateOf(0) }
    val bottomNavController = rememberNavController()


    var bottomNavList = remember {
        mutableStateOf(
            listOf(
            BottomMenu("FoodieZone", R.drawable.ic_food),
            BottomMenu("Categories", R.drawable.ic_categories),
            BottomMenu("Address", R.drawable.ic_address),
            BottomMenu("MyOrders", R.drawable.ic_order),
            )
        )
    }




    val orderItem by orderViewModel.myLastOrder.collectAsState(null)
    Scaffold(
        bottomBar = {
            Column {
                if (orderItem != null) {
//                    val orderItem = MyOrder("ORDER001",
//                        Store(
//                            1, "The Good Bowl", "Guindy", emptyList(), "1.8 km", "15-18 mins", "4.8",
//                            listOf(
//                                StoreFacilities(
//                                    1, "No Packing Charges", R.drawable.tc_tick
//                                ),
//                                StoreFacilities(
//                                    2, "Frequently reordered", R.drawable.tc_tick
//                                ),
//                                StoreFacilities(
//                                    3, "Last 50 orders without any compliant", R.drawable.tc_tick
//                                ), StoreFacilities(
//                                    4, "Loved by Delivery partner", R.drawable.tc_tick
//                                )
//                            ), emptyList(), emptyList(), false
//                        ), HashMap() )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Column(Modifier.padding(8.dp)) {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Image(
                                    painter = painterResource(id = R.drawable.bucket_briyani),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "jj",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Column(modifier = Modifier.padding(start = 8.dp), verticalArrangement = Arrangement.Center) {
                                    AppMediumTextView(
                                        text = orderItem!!.storeName,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.SemiBold,
                                        sp = 14.sp
                                    )
                                    AppMediumTextView(
                                        text = orderItem!!.storeLocation,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Normal,
                                        sp = 10.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                    }, modifier = Modifier
                                        .wrapContentWidth()
                                        .height(40.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    AppMediumTextView(
                                        "Waiting for confirmation",
                                        color = MaterialTheme.colorScheme.background,
                                        sp = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                    }

                }

                BottomNavigationBar(bottomNavList.value, selectedIndex, bottomNavController,
                    onItemSelected = { index ->
                        selectedIndex = index
                        when (bottomNavList.value[index].menuName) {
                            "FoodieZone" -> bottomNavController.navigate("FoodieZone") {
                                launchSingleTop = true
                                restoreState = true
                            }

                            "Categories" -> bottomNavController.navigate("Categories") {
                                launchSingleTop = true
                                restoreState = true
                            }

                            "Address" -> bottomNavController.navigate("Address") {
                                launchSingleTop = true
                                restoreState = true
                            }

                            "MyOrders" -> bottomNavController.navigate("MyOrders") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    })
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = "FoodieZone",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("FoodieZone") {
                FoodieZoneScreen(mainNavController, bottomNavController,foodieZoneViewModel)
            }


            composable("Categories") {
                val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel()
                val foodCategoryViewModel: FoodCategoryViewModel = hiltViewModel()
                //   CategoriesScreen(bottomNavController, foodieZoneViewModel)
                FoodCategoryScreen(
                    mainNavController,
                    bottomNavController,
                    foodieZoneViewModel,
                    foodCategoryViewModel
                )
            }

            composable("Address") {
                val addressViewModel: AddressViewModel = hiltViewModel()
                AddressScreen(bottomNavController, addressViewModel, false) }
            composable("MyOrders") {
                MyOrders(bottomNavController, orderViewModel, false) }


        }

    }

}


@Composable
fun BottomNavigationBar(
    bottomNavList: List<BottomMenu>,
    selectedIndex: Int,
    bottomNavController: NavController,
    onItemSelected: (Int) -> Unit
) {

    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background
            )
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Transparent),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsIndexed(bottomNavList) { index, item ->
                val isSelected = currentRoute == item.menuName
                Box(modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
                    .clickable(indication = null, interactionSource = remember {
                        MutableInteractionSource()
                    }) { onItemSelected(index) }
                  ) {


                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally // Center icon + text
                    ) {
                        Spacer(modifier = Modifier
                            .height(4.dp)
                            .width(70.dp)
                            .background(if (isSelected) orangeNavItem else Transparent))
                        Icon(
                            painter = painterResource(id = item.menuImage),
                            contentDescription = "item",
                            modifier = Modifier
                                .size(25.dp)
                                .padding(top = 6.dp),
                            tint = if (isSelected) orangeNavItem else LightGray
                        )


                        Text(
                            text = item.menuName,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            maxLines = 1,
                            color = if (isSelected) orangeNavItem else LightGray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun FoodItemView(list: List<Food>, onItemSelected: (Food) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)

    ) {
        list.forEach { item ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onItemSelected(item)
                    }


            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = item.foodImage),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "jj",
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()


                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        darkGreen
                                    )
                                    .padding(4.dp)
                            ) {
                                AppRatingView(
                                    modifier = Modifier,
                                    text = item.rating,
                                    ratingImage = R.drawable.ic_star_filled
                                )
                            }
                        }





                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                ) {
                                    AppSmallTextView(
                                        text = "15-20 mins . 1.9 km",
                                        modifier = Modifier
                                            .background(LightGray)
                                            .wrapContentWidth()
                                            .padding(4.dp),
                                        fontSize = 10.sp

                                    )


                                }


//
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppSmallTextView(
                                    text = item.foodName,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .padding(start = 8.dp),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                AppSmallTextView(
                                    text = item.priceCurrency + item.price,
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                    }

                    Column(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp
                        )
                    ) {

                        AppMediumTextView(
                            text = item.hotelName,
                            modifier = Modifier.padding(top = 4.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Icon(
                                painterResource(id = R.drawable.ic_offer),
                                contentDescription = "jj",
                                modifier = Modifier
                                    .size(12.dp),
                                tint = MaterialTheme.colorScheme.tertiary

                            )
                            AppSmallTextView(
                                text = item.offer,
                                modifier = Modifier.padding(start = 4.dp),
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )


                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painterResource(id = R.drawable.ic_tick_circle),
                                contentDescription = "jj",
                                modifier = Modifier
                                    .size(12.dp),
                                tint = darkGreen

                            )
                            AppSmallTextView(
                                text = "Quick Delivery Available",
                                modifier = Modifier.padding(start = 4.dp),
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                        }
                    }


                }

            }
        }
        if (list.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    // }
}


@Composable
fun HomeScreenOffers(offers: List<Offers>) {
    val pagerState = rememberPagerState(pageCount = { offers.size })



    LaunchedEffect(pagerState) {
        while (true) {
            delay(2000L)
            val nextpage = (pagerState.currentPage + 1) % offers.size
            pagerState.animateScrollToPage(nextpage)
        }

    }
    Box(modifier = Modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) { page ->
            OfferCard(offers[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(offers.size) { index ->
                val color =
                    if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(color, shape = CircleShape)
                )
            }
        }

    }
}

@Composable
fun OfferCard(offers: Offers) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {

        Image(
            painter = painterResource(id = offers.offerImage),
            contentDescription = "test",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
        )
    }
}


@Composable
fun CategoryItem(
    list: FoodCategory,
    index: Int,
    onItemSelected: (Int) -> Unit,
    foodCatSelected: Int
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 2.dp)
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }) {

                onItemSelected(list.categoryId)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (foodCatSelected == list.categoryId) {
                        MaterialTheme.colorScheme.primary

                    } else
                        MaterialTheme.colorScheme.background
                )
        ) {
            Image(
                painter = painterResource(id = list.categoryImage),
                contentDescription = "food",
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center),
            )
        }

        AppSmallTextView(
            text = list.categoryName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(60.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }


    val suggestions = listOf(
        "search \"biryani\"",
        "search \"ice cream\"",
        "search \"egg fried rice\"",
        "search \"pizza\"",
        "search \"burger\""
    )

    var currentSuggestionIndex by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            currentSuggestionIndex = (currentSuggestionIndex + 1) % suggestions.size
        }
    }


    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { AppSmallTextView(text = suggestions[currentSuggestionIndex]) },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp) // Set minimum height
            .defaultMinSize(minHeight = 1.dp)
            .clip(RoundedCornerShape(16.dp)), // Clip the TextField itself
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "search"
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent
        )
    )
}


@Composable
fun AutoScrollingLazyRow(items: List<RailIOrderItem>, scrollSpeed: Int = 6) {
    val listState = rememberLazyListState()
    val infiniteItems = remember { items + items }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16L)
            val visibleItem = listState.firstVisibleItemIndex
            val offset = listState.firstVisibleItemScrollOffset + scrollSpeed
            listState.scrollToItem(index = visibleItem, scrollOffset = offset)

            if (visibleItem >= items.size) {
                listState.scrollToItem(0, 0)
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(infiniteItems) { item ->
            RailItemCard(item)
        }
    }
}

@Composable
fun RailItemCard(item: RailIOrderItem) {
    Card(
        modifier = Modifier
            .width(200.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.clip(RoundedCornerShape(5.dp))) {
                    Image(
                        painter = painterResource(id = item.railImage),
                        contentDescription = "food",
                        modifier = Modifier
                            .size(30.dp),
                    )
                }
                AppSmallTextView(
                    item.title,
                    maxlines = 2,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }


        }
    }
}


class FakeOrderDetailDao : OrderDetailDao{
    override suspend fun insertOrderDetail(myOrderDetail: MyOrderDetail) {
        TODO("Not yet implemented")
    }

    override fun getOrderDetail(orderId: String): Flow<List<MyOrderDetail>> {
        TODO("Not yet implemented")
    }

}


@Preview(name = "Phone - Pixel 4", device = Devices.PIXEL_4)
@PreviewLightDark
@Preview(name = "Tablet - 10 inch", device = "spec:width=800dp,height=1280dp,dpi=240")
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val fakeViewModel =
            FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository())
        HomeScreen(rememberNavController(), FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository()), OrderViewModel(StoreRepository(), FakeMyOrderDao(),FakeOrderDetailDao(), FakeAddressDao()))
    }

}