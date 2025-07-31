package com.online.order.ui.screens.home.foodCategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.ui.components.AppMediumTextView
import com.online.order.data.FoodCategory
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.navigation.FOOD_LIST_ROUTE
import com.online.order.theme.AppTheme
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel


@Composable
fun FoodCategoryScreen(
    mainNavController: NavController,
    navController: NavController,
    foodieZoneViewModel: FoodieZoneViewModel, foodCategoryViewModel: FoodCategoryViewModel
) {
    val recentList by foodCategoryViewModel.recentSearchList.collectAsState()
    val searchList by foodCategoryViewModel.foodSearchList.collectAsState()
    var searchText by remember { mutableStateOf("") }
    LaunchedEffect(searchText) {
        if (searchText.length >= 3) {
            // delay(1000L)
            foodCategoryViewModel.searchFoodList(searchText)
        } else {
            foodCategoryViewModel.clearSearchFoodList()
        }
    }

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        CategoryToolBar(searchText = searchText,
            navController = navController,
            foodieZoneViewModel = foodieZoneViewModel,
            passSearchText = { s: String ->
                searchText = s

            }
        )



        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp).background(MaterialTheme.colorScheme.background),

            ) {


            item {
                if (recentList.isNotEmpty() && searchText.isEmpty()) {
                    AppMediumTextView(
                        "MY RECENT SEARCHES ",
                        color = LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp),
                        fontWeight = FontWeight.SemiBold,
                        sp = 16.sp
                    )
                }

            }

            item {
                if (searchText.isNotEmpty()) {
                    RecentSearchList(
                        foodCategoryViewModel,
                        foodieZoneViewModel, mainNavController, searchList,
                        onDeleteItem = { list ->
                            foodCategoryViewModel.deleteRecentSearch(list)

                        }, true
                    )
                }

            }

            item {
                if (searchText.isEmpty() && searchText.isEmpty()) {
                    RecentSearchList(
                        foodCategoryViewModel,
                        foodieZoneViewModel, mainNavController, recentList,
                        onDeleteItem = { list ->
                            foodCategoryViewModel.deleteRecentSearch(list)

                        }, false
                    )
                }
            }


            item {
                if (searchText.isEmpty()) {
                    AppMediumTextView(
                        "CHOOSE YOUR FOOD TO ORDER",
                        color = LightGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp),
                        fontWeight = FontWeight.SemiBold,
                        sp = 16.sp
                    )
                }

            }

            item {
                if (searchText.isEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.heightIn(max = 10000.dp),
                        userScrollEnabled = false
                    ) {


                        itemsIndexed(foodieZoneViewModel.foodCategoryList) { index, list ->
                            Card(
                                modifier = Modifier
                                    .height(130.dp)
                                    .padding(4.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        foodieZoneViewModel.foodCategoryId = list.categoryId
                                        mainNavController.navigate("$FOOD_LIST_ROUTE/${list.categoryId}")
                                        foodCategoryViewModel.addRecentSearch(list)
                                    },
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Image(
                                        painter = painterResource(id = list.categoryImage),
                                        contentDescription = "Grid Item",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .size(80.dp)

                                    )

                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = list.categoryName,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 14.sp,
                                            modifier = Modifier
                                                .padding(top = 2.dp),
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            maxLines = 3
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
}


@Composable
fun RecentSearchList(
    foodCategoryViewModel: FoodCategoryViewModel,
    foodieZoneViewModel: FoodieZoneViewModel,
    mainNavController: NavController,
    recentList: List<FoodCategory>,
    onDeleteItem: (list: FoodCategory) -> Unit,
    isSearchList: Boolean
) {

    LazyColumn(modifier = Modifier.heightIn(max = 300.dp), userScrollEnabled = false) {
        items(recentList) { list ->

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable {
                        foodieZoneViewModel.foodCategoryId = list.categoryId
                        mainNavController.navigate("$FOOD_LIST_ROUTE/${list.categoryId}"){
                            launchSingleTop = true
                        }
                        foodCategoryViewModel.addRecentSearch(list)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = list.categoryImage),
                    contentDescription = "Grid Item",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(40.dp)
                        .background(White)
                )


                Text(
                    text = list.categoryName,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3
                )

                if (!isSearchList) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "food",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                onDeleteItem(list)
                            },
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                }


            }


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryToolBar(
    searchText: String,
    navController: NavController,
    foodieZoneViewModel: FoodieZoneViewModel,
    passSearchText: (String) -> Unit
) {


    Box(
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .background(White)
            .border(0.2.dp, MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(8.dp))

    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight().
            background(MaterialTheme.colorScheme.background)
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    passSearchText(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "search",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { navController.navigateUp() }


                    )
                },
                trailingIcon = {
                    if (searchText.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "search",
                            modifier = Modifier.clickable {
                                passSearchText("")
                            }
                        )
                    }

                },
                placeholder = {
                    Text(
                        text = "Search food or dish name...",
                        fontSize = 14.sp,
                        color = LightGray
                    )
                },
                textStyle = TextStyle(
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent
                ),
                singleLine = true,

                )
        }
    }
}

@PreviewLightDark
@Composable
fun FoodCategoryScreenPreview() {
    AppTheme {
        FoodCategoryScreen(
            rememberNavController(),
            rememberNavController(),
            FoodieZoneViewModel(
                foodRepository = FoodRepository(),
                filterRepository = FilterRepository(),
                storeRepository = StoreRepository()
            ),
            FoodCategoryViewModel(FoodRepository())
        )
    }
}