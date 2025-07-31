package com.online.order.ui.screens.home.foodiezone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppBox
import com.online.order.ui.components.AppIcon
import com.online.order.ui.components.AppLargeTextView
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.Food
import com.online.order.data.Offers
import com.online.order.data.RailIOrderItem
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import com.online.order.navigation.AuthNavGraph
import com.online.order.theme.AppTheme
import com.online.order.ui.screens.AutoScrollingLazyRow
import com.online.order.ui.screens.CategoryItem
import com.online.order.ui.screens.FoodItemView
import com.online.order.ui.screens.HomeScreenOffers
import java.io.File

@Composable
fun FoodieZoneScreen(navController: NavController, bottomNavController: NavController,foodieZoneViewModel: FoodieZoneViewModel) {

    val foodList = remember {
        mutableStateListOf<Food>()
    }


    var count by remember { mutableStateOf(0) }

    val filterList = foodieZoneViewModel.mainFoodList.filter {
        it.categoryId == foodieZoneViewModel.foodCatSelected || (foodieZoneViewModel.foodCatSelected == 1 && it.popular)
    }
    foodList.clear()
    foodList.addAll(filterList)




    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()


        ) {

            LazyColumn(
                modifier = Modifier.background(MaterialTheme.colorScheme.background)

            ) {

                item {
                    TopView(navController,
                        bottomNavController,
                        foodieZoneViewModel,
                        foodieZoneViewModel.foodCatSelected,
                        onItemSelected = { selectedCateItem ->
                            foodieZoneViewModel.foodCatSelected = selectedCateItem
                            val filterList = foodieZoneViewModel.mainFoodList.filter {
                                it.categoryId == selectedCateItem || (selectedCateItem == 1 && it.popular)
                            }
                            foodList.clear()
                            foodList.addAll(filterList)

                        })
                }

                items(foodList.chunked(2)) { list ->
                    FoodItemView(list, onItemSelected = { item ->
                        foodieZoneViewModel.selectedFoodItem = item
                        navController.navigate(AuthNavGraph.STORE.route){
                            launchSingleTop = true
                        }
                    })
                }


                item {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Image(
                            painter = painterResource(id = R.drawable.allindia),
                            contentDescription = "jj",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .padding(top = 8.dp)



                        )
                        AppLargeTextView("Made in India \uD83D\uDE0D", color = Color.LightGray, modifier = Modifier.padding(top = 8.dp))

                        AppMediumTextView(
                            "Foodie Zone",
                            color = Color.LightGray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }


                }
            }

        }

    }
}


@Composable
fun TopView(navController : NavController,bottomNavController : NavController ,viewModel: FoodieZoneViewModel, foodCatSelected: Int, onItemSelected: (Int) -> Unit) {
    var searchText by remember {
        mutableStateOf("")
    }



    val context = LocalContext.current

    var capturedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var capturedImageFile by remember {
        mutableStateOf<File?>(null)
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {success->
        if (success && capturedPhotoUri != null) {
            val inputStream = context.contentResolver.openInputStream(capturedPhotoUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            capturedBitmap = bitmap
        }else{
            capturedPhotoUri = null
        }
    }

    var isImageClicked by remember {
        mutableStateOf(false)
    }


    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
    }


    LaunchedEffect(Unit) {


    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)

    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-100.dp), y = (100.dp / 4))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)

        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (300.dp), y = (300.dp / 2))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)

        )
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
        ) {

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                if(capturedBitmap == null) {
                    AppBox(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer).clickable {
                                navController.navigate(AuthNavGraph.PROFILE.route) {
                                    launchSingleTop = true
                                }
                            }
                    ) {

                        AppIcon(
                            imageVector = Icons.Filled.Person,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
 //                               .clickable {
//                                    if (ContextCompat.checkSelfPermission(
//                                            context,
//                                            android.Manifest.permission.CAMERA
//                                        ) != PackageManager.PERMISSION_GRANTED
//                                    ) {
//                                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
//                                    } else {
//
//                                        val file = viewModel.createImageFile(context)
//                                        val uri = FileProvider.getUriForFile(
//                                            context,
//                                            "${context.packageName}.provider",
//                                            file
//                                        )
//                                        capturedImageFile = file
//                                        capturedPhotoUri = uri
//                                        launcher.launch(uri)
//                                    }


 //                                   }

 //                               },
                        )
                    }
                }else{
                    capturedBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Captured",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                }


                Column(modifier = Modifier.padding(start = 6.dp), verticalArrangement = Arrangement.Center) {
                    AppMediumTextView(
                        text = "Hello Raja",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground

                    )

                    AppSmallTextView(
                        text = "How is the Day? Let's try new snacks today",
                        modifier = Modifier.padding(top = 2.dp),
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                }



                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(end = 4.dp)
                        .clickable {
                            bottomNavController.navigate("Categories") {
                                launchSingleTop = true
                            }
                        },
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Icon(
                    painterResource(id = R.drawable.ic_notification),
                    contentDescription = "",

                    modifier = Modifier.size(25.dp).clickable {
                        navController.navigate(AuthNavGraph.NOTIFICATION_LIST.route)
                    },
                    tint = MaterialTheme.colorScheme.onBackground
                )

            }

            AutoScrollingLazyRow(
                listOf(
                    RailIOrderItem(1,"Order your Fav Food and get delivered in 15-20 mins!!", R.drawable.delivery),
                    RailIOrderItem(2,"celebrate your special day with Fondant Cake", R.drawable.redvelvetcake),
                    RailIOrderItem(3,"Bucket Briyani with 40% Offer, order now and Enjoy with your Family", R.drawable.bucket_briyani),
                    RailIOrderItem(4,"Maa special black forest bent a cake and have it", R.drawable.blackforestcake)
                )
            )
            Box(
                modifier = Modifier

            ) {

                val offers = listOf(
                    Offers(
                        " one plus one deal on your first order!", R.drawable.offer4
                    ),
                    Offers("Chinese New Year Special", R.drawable.offer2),
                    Offers("Free Delivery over ₹299", R.drawable.briyanioffer),
                    Offers("Limited Time Deal: 75% OFF", R.drawable.bk),
                    Offers("Pizza Special mania Offer", R.drawable.offer3)
                )

                HomeScreenOffers(offers = offers)
            }

            LazyRow {
                itemsIndexed(viewModel.foodCategoryList) { index, list ->
                    CategoryItem(list = list, index = index, onItemSelected, foodCatSelected)
                }
            }

            val catName =
                viewModel.foodCategoryList.find { it.categoryId == foodCatSelected }?.categoryName
                    ?: ""

            AppLargeTextView(
                text = catName,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )


        }
    }

//    if(isImageClicked){
//        val file = viewModel.createImageFile(context)
//        val uri = FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.provider",
//            file
//        )
//
//
////        CheckCameraPermission(capturePhoto = {
////            capturedPhotoUri = uri
////            capturedImageFile = file
////            launcher.launch(uri)
////        })
//
//
//    }

}




//@Composable
//fun CheckCameraPermission(capturePhoto : () -> Unit){
//    val context = LocalContext.current
//
//
//}




@Preview(name = "Phone - Pixel 4", device = Devices.PIXEL_4)
@PreviewLightDark
@Preview(name = "Tablet - 10 inch", device = "spec:width=800dp,height=1280dp,dpi=240")
@Composable
fun HomeScreenPreview() {
    AppTheme {
        val viewModel = FoodieZoneViewModel(FoodRepository(), FilterRepository(), StoreRepository())
        FoodieZoneScreen(rememberNavController(), rememberNavController(), viewModel)
    }

}
