package com.online.order.ui.screens.home.order

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppLargeTextView
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.Food
import com.online.order.data.Store
import com.online.order.data.StoreFacilities
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.database.address.AddressDao
import com.online.order.database.address.AddressEntity
import com.online.order.database.myorder.MyOrders
import com.online.order.database.myorder.MyOrdersDao
import com.online.order.database.myorderDetail.MyOrderDetail
import com.online.order.database.myorderDetail.MyOrderWithDetails
import com.online.order.database.myorderDetail.OrderDetailDao
import com.online.order.navigation.AuthNavGraph
import com.online.order.ui.screens.home.address.AddressBookItem
import com.online.order.ui.screens.home.address.AddressViewModel
import com.online.order.theme.AppTheme
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController,
    foodieZoneViewModel: FoodieZoneViewModel,
    orderViewModel: OrderViewModel,
    addressViewModel: AddressViewModel = hiltViewModel()
) {


    var context = LocalContext.current
    var selectedStore = foodieZoneViewModel.getStoreDetails()
    val scope = rememberCoroutineScope()

    var showSheet by remember {
        mutableStateOf(false)
    }

    var bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)


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

    val orderedMapList = orderViewModel.getOrderedMapListByStoreId(selectedStore.storeId)
    val totalAmount by remember {
        derivedStateOf { orderViewModel.getTotalOrderAmount(selectedStore.storeId) }
    }


    var profileResult by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        orderViewModel.uiEvent.collect { event ->
            when (event) {
                is OrderViewModel.UiEvent.ShowSnackBar -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is OrderViewModel.UiEvent.NavigateToOrderConfirmation -> {
                    navController.navigate(AuthNavGraph.ORDER_CONFIRM.route) {
                        popUpTo(AuthNavGraph.STORE.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

                is OrderViewModel.UiEvent.BottomSheetState -> {
                    if (event.dismissBottomSheet) {
                        showSheet = false
                        bottomSheetState.hide()
                        Toast.makeText(context, "Address Changed successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 50.dp)
    ) {


        OrderTitleBar(navController, selectedStore)
        Box(modifier = Modifier) {
            Column(modifier = Modifier) {

                OrderedItems(
                    orderedMapList,
                    totalAmount,
                    orderViewModel,
                    onAddressChange = {
                        scope.launch {
//                            val profile = async(Dispatchers.IO){
//                             fetchUserProfile()
//
//                            }
//                            profileResult = profile.await()


                            showSheet = true
                            bottomSheetState.show()
                        }
                    })

            }
            if (orderedMapList.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(modifier = Modifier.clickable { }) {
                                AppSmallTextView(
                                    "PAY USING",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                AppMediumTextView(
                                    "Google Pay UPI",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable {
                                        orderViewModel.orderedFoodList(orderedMapList)

                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row {
                                        AppMediumTextView(
                                            "₹",
                                            modifier = Modifier,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start,
                                            sp = 16.sp,
                                            color = White
                                        )
                                        AppMediumTextView(
                                            totalAmount.toString(),
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Start,
                                            sp = 16.sp,
                                            color = White
                                        )
                                    }

                                    AppMediumTextView(
                                        "TOTAL",
                                        textAlign = TextAlign.Start,
                                        color = White
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                AppMediumTextView(
                                    "PLACE ORDER",
                                    fontWeight = FontWeight.SemiBold,
                                    color = White,
                                    modifier = Modifier.padding(12.dp)
                                )

                            }
                        }


                    }
                }
            }

        }


        if (showSheet) {
            val addressList by addressViewModel.addressList.collectAsState(emptyList())
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        showSheet = false
                        bottomSheetState.hide()
                        addressViewModel.resetEditAddress()
                    }


                }, sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(12.dp)
            ) {
                AddressListSheet(addressList, addressViewModel, onItemClick = { addressId ->
                    scope.launch {
                        orderViewModel.getSelectedAddress(addressId)
                    }

                })
            }

        }


    }
}

suspend fun fetchUserProfile(): String {
    delay(1000)
    return "John Doe"
}


@Composable
fun OrderTitleBar(navController: NavController, selectedStore: Store) {
    Column(
        modifier = Modifier.padding(
            top = WindowInsets.statusBars
                .asPaddingValues()
                .calculateTopPadding(),
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .height(40.dp)
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
fun OrderedItems(
    orderedMapList: MutableMap<Int, MutableList<Food>>,
    totalAmount: Int,
    orderViewModel: OrderViewModel,
    onAddressChange: () -> Unit
) {


    LazyColumn(
        modifier = Modifier.padding(
            top = 12.dp,
            bottom = if (orderedMapList.isNotEmpty()) 80.dp else 30.dp
        )
    ) {


//        if (orderViewModel.orderedMapList.size == 0) {
//            orderViewModel.orderedMapList.getOrPut(2) { mutableStateListOf() }.add(
//                Food(
//                    "Mutton Curry",
//                    R.drawable.muttoncurry1,
//                    "The Good Bowl",
//                    "4.5",
//                    "",
//                    "Get selected items @ ₹199 only", true, 10, 33, 250, 2, "₹", 1,
//                    0.9f,
//                    "A dish made of spiced mutton meatballs, deep fried to perfection",
//                    1,
//                    true,
//                    listOf(
//                        R.drawable.muttoncurry1,
//                        R.drawable.muttoncurry1,
//                        R.drawable.muttoncurry1
//                    )
//                )
//            )
//        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
            SavedOrderView()
        }

        item {
            AppMediumTextView(
                "Your Ordered Items",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                fontWeight = FontWeight.SemiBold
            )
        }

        items(orderedMapList.entries.toList()) { (foodId, foodList) ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)

            ) {

                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    foodList.firstOrNull()?.foodImage?.let { painterResource(id = it) }?.let {
                        Image(
                            painter = it,
                            contentScale = ContentScale.Crop,
                            contentDescription = "jj",
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)


                        )
                    }

                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        val foodName = foodList.firstOrNull()?.foodName ?: "Unknown Food"
                        AppMediumTextView(foodName, color = MaterialTheme.colorScheme.onBackground)

                        AppSmallTextView(
                            "Edit",
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(modifier = Modifier
                            .width(90.dp)
                            .height(30.dp)
                            .clickable {

                            }
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {


                            if (foodList.isNotEmpty()) {
                                AppSmallTextView(
                                    text = "-",
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .clickable {
                                            val food = foodList.last()
                                            orderViewModel.removeOrderedItem(food)

                                        },
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 16.sp,
                                    maxlines = 2,
                                    textAlign = TextAlign.Center,

                                    )
                            }





                            AppSmallTextView(
                                text = if ((orderedMapList[foodId]?.size
                                        ?: 0) > 0
                                ) orderedMapList[foodId]?.size.toString() else "ADD",
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
                                        if (foodList.size > 0) {
                                            val food = foodList[foodList.size - 1]
                                            orderViewModel.addOrderedItem(food)
                                        }

                                    },
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 16.sp,
                                maxlines = 2,
                                textAlign = TextAlign.Center,

                                )

                        }

                        Spacer(modifier = Modifier.padding(2.dp))

                        AppSmallTextView(
                            "Price: ${foodList.firstOrNull()?.priceCurrency + foodList.firstOrNull()?.price}",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }


                }
            }
        }

        item {
            DeliveryView(totalAmount, orderViewModel, onAddressChange = {
                onAddressChange()

            })
            HalfPaintedBox()
            // DonationView()
            CancellationPolicyView()
            EndOrderView()
        }


    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DeliveryView(totalAmount: Int, orderViewModel: OrderViewModel, onAddressChange: () -> Unit) {

    val lastAddedAddress by orderViewModel.lastAddedAddress.collectAsState(initial = null)
    val selectedAddress by orderViewModel.updatedAddress.collectAsState(null)

    val addressForDelivery = selectedAddress ?: lastAddedAddress


    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp)

    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delivery_scooter),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                AppMediumTextView(
                    "Delivery in",
                    modifier = Modifier.padding(start = 6.dp),
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground,
                    sp = 14.sp
                )
                AppMediumTextView(
                    "15-20 mins",
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    sp = 14.sp
                )
            }

        }

        AppSmallTextView(
            "- ".repeat(80),
            fontWeight = FontWeight.Normal,
            color = LightGray,
            maxlines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_arrow),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)

                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.wrapContentWidth()) {
                        AppMediumTextView(
                            "Delivery at ",
                            modifier = Modifier.padding(start = 6.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            sp = 14.sp
                        )
                        addressForDelivery?.let {
                            AppMediumTextView(
                                it.locationType,
                                modifier = Modifier.padding(start = 2.dp),
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground,
                                sp = 14.sp
                            )
                        }
                    }
                    addressForDelivery?.let {
                        AppMediumTextView(
                            it.fullAddress,
                            modifier = Modifier.padding(top = 2.dp, start = 4.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground,
                            sp = 14.sp,
                        )
                    }
                }

                AppSmallTextView(
                    text = "Change",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 8.dp)
                        .clickable {
                            onAddressChange()

                        },
                    fontWeight = FontWeight.Bold,
                    color = White,
                    fontSize = 14.sp,
                    maxlines = 2,
                    textAlign = TextAlign.Center,

                    )


            }


        }
        AppSmallTextView(
            "- ".repeat(80),
            fontWeight = FontWeight.Normal,
            color = LightGray,
            overflow = TextOverflow.Clip,
            maxlines = 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                AppMediumTextView(
                    (addressForDelivery?.name ?: "") + ",",
                    modifier = Modifier.padding(start = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    sp = 14.sp
                )
                AppMediumTextView(
                    "+91 " + (addressForDelivery?.phoneNumber ?: ""),
                    modifier = Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    sp = 14.sp
                )
            }

        }

        AppSmallTextView(
            "- ".repeat(80),
            fontWeight = FontWeight.Normal,
            color = LightGray,
            overflow = TextOverflow.Clip,
            maxlines = 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_bill_check),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Column {
                    Row {
                        AppMediumTextView(
                            "Total Bill ",
                            modifier = Modifier.padding(start = 6.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground, sp = 14.sp
                        )

                        AppMediumTextView(
                            "₹$totalAmount",
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground, sp = 14.sp
                        )
                    }
                    AppMediumTextView(
                        "Incl.taxes and charges",
                        modifier = Modifier.padding(start = 6.dp),
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground, sp = 12.sp
                    )
                }

            }

        }
    }
}


@Composable
fun AddressListSheet(
    addressList: List<AddressEntity>,
    addressViewModel: AddressViewModel,
    onItemClick: (Int) -> Unit
) {

    var addressSelected by remember {
        mutableStateOf<AddressEntity?>(null)
    }
    Column {
        AppMediumTextView(
            "Please select your address",
            fontWeight = FontWeight.Bold,
            sp = 16.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(addressList) { address ->
                AddressBookItem(addressViewModel,
                    address,
                    isExpanded = address == addressSelected,
                    onMenuClick = { addressEntity ->
                        addressSelected = addressEntity

                    },
                    onEditClick = { editAddressfromList ->
                        if (editAddressfromList != null) {
                            addressViewModel.getAddressById(editAddressfromList)
                        }
                    },
                    onItemClick = { addressId ->
                        onItemClick(addressId)
                    })
            }
        }
    }

}


@Composable
fun HalfPaintedBox() {

    val bgColor = MaterialTheme.colorScheme.secondaryContainer
    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)

                .background(MaterialTheme.colorScheme.surface)
        ) {

            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    moveTo(0f, height * 0.8f)
                    quadraticBezierTo(
                        width / 2f, height * 0.8f,
                        width, height * 0.4f
                    )
                    lineTo(width, 0f)
                    lineTo(0f, 0f)
                    close()
                }

                drawPath(
                    path = path,
                    color = bgColor // Painted curve color
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_child),
                        contentDescription = "tets",
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.padding(start = 6.dp)) {
                        AppMediumTextView(
                            "Let's serve a brighter future",
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        AppSmallTextView(
                            "Help us reduce food waste by avoiding cancellation after placing your order. A 100% cancellation fee will be applied. ",
                            modifier = Modifier.padding(top = 2.dp),
                            fontWeight = FontWeight.Normal,
                            maxlines = 4,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }

            }

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppMediumTextView(
                    "Donate to Feeding India",
                    modifier = Modifier,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier
                    .width(90.dp)
                    .height(30.dp)
                    .clickable {

                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {


                    AppSmallTextView(
                        text = "ADD",
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Normal,
                        color = White,
                        fontSize = 14.sp,
                        maxlines = 2,
                        textAlign = TextAlign.Center,

                        )


                }
            }
        }
    }
}


@Composable
fun DonationView() {
    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
            Column(modifier = Modifier.padding(12.dp)) {
                AppMediumTextView(
                    "Let's serve a brighter future",
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
                AppSmallTextView(
                    "Help us reduce food waste by avoiding cancellation after placing your order. A 100% cancellation fee will be applied. ",
                    modifier = Modifier.padding(top = 2.dp),
                    fontWeight = FontWeight.Normal,
                    color = Gray,
                    maxlines = 2
                )
            }

            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppMediumTextView(
                    "Donate to Feeding India",
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier
                    .width(90.dp)
                    .height(30.dp)
                    .clickable {

                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {


                    AppSmallTextView(
                        text = "ADD",
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Normal,
                        color = White,
                        fontSize = 14.sp,
                        maxlines = 2,
                        textAlign = TextAlign.Center,

                        )


                }
            }
        }


    }
}

@Composable
fun EndOrderView() {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {

        Image(
            painter = painterResource(id = R.drawable.allindia),
            contentDescription = "jj",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(top = 8.dp)


        )
        AppLargeTextView(
            "Made in India \uD83D\uDE0D",
            color = LightGray,
            modifier = Modifier.padding(top = 8.dp)
        )

        AppMediumTextView(
            "Foodie Zone",
            color = LightGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }

}

@Composable
fun CancellationPolicyView() {
    Column(modifier = Modifier.padding(12.dp)) {
        AppMediumTextView(
            "CANCELLATION POLICY",
            modifier = Modifier.padding(vertical = 4.dp),
            fontWeight = FontWeight.Normal,
            color = Gray,
            sp = 16.sp
        )
        AppSmallTextView(
            "Help us reduce food waste by avoiding cancellation after placing your order. A 100% cancellation fee will be applied. ",
            modifier = Modifier.padding(top = 2.dp),
            fontWeight = FontWeight.Normal,
            color = Gray,
            maxlines = 2
        )
    }
}

@Composable
fun SavedOrderView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(vertical = 12.dp)
    ) {
        AppSmallTextView(
            text = "\uD83E\uDD73  You Saved 10 on this order",
            modifier = Modifier.padding(start = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

class FakeMyOrderDao : MyOrdersDao {
    override suspend fun insertMyOrder(myOrders: MyOrders) {
        TODO("Not yet implemented")
    }

    override fun getOrders(): Flow<List<MyOrderWithDetails>> {
        TODO("Not yet implemented")
    }

    override fun getLastOrder(): Flow<MyOrders> {
        TODO("Not yet implemented")
    }

    override suspend fun getLastOrderNumber(): String {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrder(orderId: String) {
        TODO("Not yet implemented")
    }

}

class FakeAddressDao : AddressDao {
    override suspend fun insertAddress(address: AddressEntity) {
        TODO("Not yet implemented")
    }

    override fun getAllAddresses(): Flow<List<AddressEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun editAddress(id: Int): AddressEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun updateAddress(address: AddressEntity) {
        TODO("Not yet implemented")
    }

    override fun getLastAddedAddress(): Flow<AddressEntity?> {
        TODO("Not yet implemented")
    }

    override fun getSelectedAddress(id: Int): Flow<AddressEntity?> {
        TODO("Not yet implemented")
    }

}

class  FakeOrderDetailDao : OrderDetailDao {
    override suspend fun insertOrderDetail(myOrderDetail: MyOrderDetail) {
        TODO("Not yet implemented")
    }

    override fun getOrderDetail(orderId: String): Flow<List<MyOrderDetail>> {
        TODO("Not yet implemented")
    }

}

@PreviewLightDark
@Composable
fun OrderScreenPreview() {
    AppTheme {
        OrderScreen(
            rememberNavController(),
            FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository()),
            OrderViewModel(
                StoreRepository(),
                FakeMyOrderDao(),
                FakeOrderDetailDao(),
                FakeAddressDao()
            ),
            AddressViewModel(FakeAddressDao())
        )
    }
}