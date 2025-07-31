package com.online.order.ui.screens.home.foodList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
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
import com.online.order.data.Food
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.navigation.AuthNavGraph
import com.online.order.theme.AppTheme
import com.online.order.theme.orangeNavItem
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel
import kotlinx.coroutines.delay

@Composable
fun FoodListScreen(
    navController: NavController,
    foodieZoneViewModel: FoodieZoneViewModel,
    foodListViewModel: FoodListViewModel,
    categoryId: Int
) {
    val scrollState = rememberLazyListState()
    val foodList by foodListViewModel.finalFoodList.collectAsState()

    LaunchedEffect(Unit) {
        foodListViewModel.prepareSubCategory(categoryId)
        foodListViewModel.prepareFoodList(categoryId)
        foodListViewModel.getCategoryName(categoryId)

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding(), bottom = 30.dp
            )
    ) {
        FoodToolBar(navController, foodListViewModel)
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            userScrollEnabled = true
        ) {
            item {
                FoodCategoryList(foodListViewModel, onItemSelected = { categoryId ->
                    foodListViewModel.subCategorySelected = categoryId
                    foodListViewModel.clearAndAddNewSubFoodFilterList(categoryId)
                }, foodListViewModel.subCategorySelected)
            }

            item {
                FoodItemFilterList(foodieZoneViewModel)
            }

            item {
                AppMediumTextView(
                    "ALL RESTAURANTS",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(foodList) { list ->
                SingleFoodItem(list, onItemSelected = { item ->
                    foodieZoneViewModel.selectedFoodItem = item
                    navController.navigate(AuthNavGraph.STORE.route){ launchSingleTop = true}
                })
            }
        }
    }
}


@Composable
fun SingleFoodItem(item: Food, onItemSelected: (Food) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { item.largeImageList.size })
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2000L)
            val nextpage = (pagerState.currentPage + 1) % item.largeImageList.size
            pagerState.animateScrollToPage(nextpage)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clickable {
                    onItemSelected(item)
                }
        ) {

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()

            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { page ->
                    FoodItemPagerView(item.largeImageList[page])
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .wrapContentWidth()
                        ) {
                            AppSmallTextView(
                                text = "15-20 mins . 1.9 km",
                                modifier = Modifier
                                    .background(LightGray)
                                    .wrapContentWidth()
                                    .padding(6.dp),
                                fontSize = 10.sp

                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))


                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(bottom = 10.dp, end = 10.dp)
                        ) {
                            repeat(item.largeImageList.size) { index ->
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.tertiaryContainer),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppSmallTextView(
                            text = item.foodName,
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp)
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
                AppLargeTextView(
                    text = item.hotelName,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }


        }
    }
}


@Composable
fun FoodItemPagerView(offer: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Image(
            painter = painterResource(id = offer),
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
fun FoodCategoryList(
    foodListViewModel: FoodListViewModel,
    onItemSelected: (Int) -> Unit,
    foodCatSelected: Int
) {

    val SubCategoryList by foodListViewModel.selectedSubCategoryList.collectAsState()
    LazyRow(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        userScrollEnabled = true
    ) {
        itemsIndexed(SubCategoryList) { index, list ->
            Column(
                modifier = Modifier.padding(vertical = 2.dp).height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .clip(CircleShape)
                        .clickable {
                            onItemSelected(list.subCategoryId)
                        }
                ) {
                    Image(
                        painter = painterResource(id = list.categoryImage),
                        contentDescription = "food",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(70.dp)
                            .align(Alignment.Center),
                    )
                }

                Text(
                    text = list.categoryName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(top = 2.dp)
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 12.sp,
                    maxLines = 2
                )

                Spacer(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(2.dp)
                        .width(90.dp)
                        .background(
                            if (foodCatSelected == list.subCategoryId)
                                MaterialTheme.colorScheme.primary
                            else
                                Transparent
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemFilterList(foodieZoneViewModel: FoodieZoneViewModel) {
    LazyRow(
        modifier = Modifier
            .padding(top = 8.dp)
    ) {
        items(foodieZoneViewModel.filterItemList) { list ->
            Card(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = list.filterImage),
                        contentDescription = "filter",
                        modifier = Modifier
                            .size(16.dp)

                    )
                    Text(
                        text = list.filterName,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 6.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
            }
        }
    }
}

@Composable
fun FoodToolBar(navController: NavController, foodListViewModel: FoodListViewModel) {
    val selectedCategoryName by foodListViewModel.selectedCategoryName.collectAsState()
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(0.5.dp, MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(8.dp))

    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(4.dp)
        ) {

            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "food",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigateUp()
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = selectedCategoryName,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 14.sp,
                    color = orangeNavItem,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
@PreviewLightDark
fun FoodListScreenPreview() {
    AppTheme {
        FoodListScreen(
            rememberNavController(),
            FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository()), FoodListViewModel(
                FoodRepository()
            ),
            1
        )
    }
}