package com.online.order.ui.screens.home.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.online.order.ui.components.AppRatingView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.Food
import com.online.order.data.FoodCategory
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.theme.AppTheme
import com.online.order.theme.lightGrey
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavController, foodieZoneViewModel: FoodieZoneViewModel) {

    val scrollState = rememberLazyListState()

    var foodCatSelected by remember {
        mutableIntStateOf(2)
    }

    val foodList = remember {
        mutableStateListOf<Food>()
    }

    val filteredList = foodieZoneViewModel.mainFoodList.filter {
        it.categoryId == foodCatSelected
    }
    foodList.clear()
    foodList.addAll(filteredList)

    Column(modifier = Modifier) {

        CategoryTopView(navController)
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        )
        {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(70.dp)
                    .background(Color.White),
                userScrollEnabled = true
            ) {
                // Lazy list items
                itemsIndexed(foodieZoneViewModel.foodCategoryList) { index, list ->
                    Column(
                        modifier = Modifier.padding(vertical = 6.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(60.dp)
                                .clip(CircleShape)
                                .background(
                                    if (foodCatSelected == list.categoryId)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        lightGrey
                                )
                                .clickable {
                                    foodCatSelected = list.categoryId
                                    val filterList = foodieZoneViewModel.mainFoodList.filter {
                                        it.categoryId == list.categoryId
                                    }
                                    foodList.clear()
                                    foodList.addAll(filterList)
                                }
                        ) {
                            Image(
                                painter = painterResource(id = list.categoryImage),
                                contentDescription = "food",
                                modifier = Modifier
                                    .size(30.dp)
                                    .align(Alignment.Center),

                                )
                        }

                        Text(
                            text = list.categoryName,   // assuming categoryName is a string field
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(80.dp)
                                .align(Alignment.CenterHorizontally),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color.Black,
                            maxLines = 2
                        )
                    }
                }
            }

            Column(modifier = Modifier) {
                LazyRow(modifier = Modifier.background(Color.White)) {
                    items(foodieZoneViewModel.filterList) { list ->
                        Card(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .background(Color.White)
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
                                    modifier = Modifier.padding(start = 2.dp)
                                )

                            }
                        }

                    }
                }

                LazyColumn(
                    modifier = Modifier.background(Color.White)

                ) {

                    items(foodList) { list ->

                                   Card(
                                       colors = CardDefaults.cardColors(containerColor = Color.White),
                                       elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                       , modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).padding(8.dp)
                                        ) {

                                       Column(
                                           modifier = Modifier
                                               .padding(horizontal = 8.dp, vertical = 8.dp)

                                               .clickable {
                                               }
                                           , horizontalAlignment = Alignment.CenterHorizontally


                                       ) {

                                           Box(modifier = Modifier) {
                                               Image(
                                                   painter = painterResource(id = list.foodImage),
                                                   contentScale = ContentScale.Fit,
                                                   contentDescription = "jj",
                                                   modifier = Modifier
                                                       .height(150.dp)
                                                       .fillMaxWidth()
                                                       .align(Alignment.Center)



                                               )
                                               Row(modifier = Modifier.align(Alignment.BottomStart).clip(RoundedCornerShape( topEnd = 16.dp, bottomEnd = 16.dp)).background(MaterialTheme.colorScheme.tertiaryContainer), verticalAlignment = Alignment.CenterVertically) {
                                               Text(
                                                   text = list.foodName,
                                                   modifier = Modifier
                                                       .wrapContentWidth()
                                                       .padding(4.dp),
                                                   fontSize = 10.sp,
                                                   color = Color.Black,
                                                   maxLines = 1,
                                                   fontWeight = FontWeight.SemiBold
                                               )

                                                   AppMediumTextView(
                                                       text = " - ₹70",
                                                       modifier = Modifier
                                                           .wrapContentWidth()
                                                           .background(MaterialTheme.colorScheme.tertiaryContainer)
                                                           .padding(end = 8.dp),
                                                       color = Color.Black,
                                                       fontWeight = FontWeight.SemiBold
                                                   )
                                               }
                                           }
                                           Column(
                                               modifier = Modifier
                                                   .fillMaxWidth()
                                                   .background(lightGrey),
                                               verticalArrangement = Arrangement.Center
                                           ) {
                                               AppSmallTextView(
                                                   text = "15-20 mins . 1.9 km",
                                                   modifier = Modifier
                                                       .fillMaxWidth()
                                                       .clip(RoundedCornerShape(topStart = 8.dp))
                                                       .background(Color.LightGray)
                                                       .padding(4.dp),
                                                   fontSize = 10.sp

                                               )

                                               Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                                                   AppLargeTextView(
                                                       text = list.hotelName,
                                                       modifier = Modifier
                                                           .weight(1f)
                                                           .padding(3.dp)
                                                           .fillMaxWidth(),
                                                       color = Color.Black,
                                                       fontWeight = FontWeight.ExtraBold
                                                   )

                                                   Box(modifier = Modifier
                                                       .clip(RoundedCornerShape(8.dp))
                                                       .background(MaterialTheme.colorScheme.primary)
                                                       .padding(4.dp)){
                                                       AppRatingView(
                                                           text = list.rating,
                                                           ratingImage = R.drawable.ic_star_filled,
                                                           modifier = Modifier,
                                                           color = Color.White,
                                                           tintColor = Color.White

                                                       )

                                                   }

                                               }

                                               Row(
                                                   modifier = Modifier
                                                       .fillMaxWidth()
                                                       .padding(4.dp)
                                               ) {

                                                   Icon(
                                                       painterResource(id = R.drawable.ic_offer),
                                                       contentDescription = "jj",
                                                       modifier = Modifier
                                                           .size(12.dp),
                                                       tint = MaterialTheme.colorScheme.tertiary

                                                   )
                                                   AppSmallTextView(
                                                       text = list.offer,
                                                       modifier = Modifier.padding(start = 4.dp),
                                                       fontWeight = FontWeight.Light,
                                                       color = Color.Black,
                                                       fontSize = 10.sp
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
fun CategoryTopView(navController: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }

    var catList = remember {
        mutableStateListOf(
            FoodCategory(1,"Popular Food", R.drawable.ic_fav),
            FoodCategory(2,"Ice Cream", R.drawable.ic_icecream),
            FoodCategory(3,"Soft Drinks", R.drawable.ic_drinks),
            FoodCategory(4,"Cakes", R.drawable.ic_cake),
            FoodCategory(5,"Meat", R.drawable.ic_meat),
            FoodCategory(6,"Burgers", R.drawable.ic_burger)
        )
    }


    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(Color.White)
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier.wrapContentHeight()
        ) {

            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "food",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            navController.navigateUp()
                        },
                    tint = Color.Black
                )
                Text(
                    text = "Categories",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))




                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "food",
                    modifier = Modifier
                        .size(25.dp),
                    tint = Color.Black
                )


            }


        }
    }
}


@Composable
@PreviewLightDark
fun CategoriesScreenPreview() {
    AppTheme {
        CategoriesScreen(rememberNavController(), FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository()) )
    }
}
