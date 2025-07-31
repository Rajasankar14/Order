@file:OptIn(ExperimentalAnimationApi::class)

package com.online.order.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.online.order.ui.screens.AboutScreen
import com.online.order.ui.screens.HomeScreen
import com.online.order.ui.screens.NotificationScreen
import com.online.order.ui.screens.SplashScreen
import com.online.order.ui.screens.home.address.AddressScreen
import com.online.order.ui.screens.home.address.AddressViewModel
import com.online.order.ui.screens.StoreScreen
import com.online.order.ui.screens.home.foodList.FoodListScreen
import com.online.order.ui.screens.home.foodList.FoodListViewModel
import com.online.order.ui.screens.home.foodiezone.FoodieZoneViewModel
import com.online.order.ui.screens.home.myOrder.MyOrders
import com.online.order.ui.screens.home.order.OrderScreen
import com.online.order.ui.screens.home.order.OrderSuccessScreen
import com.online.order.ui.screens.home.order.OrderViewModel
import com.online.order.ui.screens.profile.ProfileScreen
import com.online.order.ui.screens.profile.ProfileViewModel
import com.online.order.ui.screens.profileUser.ProfileUserScreen

enum class AuthNavGraph(val route: String) {

    SPLASH("splash"),
    LOGIN("login"),
    HOME("home"),
    STORE("StoreScreen"),
    FOOD_LIST("$FOOD_LIST_ROUTE/{categoryId}"),
    ORDER("OrderScreen"),
    ORDER_CONFIRM("OrderConfirm"),
    PROFILE("profile"),
    ABOUT("about"),
    MY_ORDERS("myorders"),
    ADDRESS("Address"),
    NOTIFICATION_LIST("Notification"),
    USEPROFILE("UserProfile")
}

const val FOOD_LIST_ROUTE = "foodList"

@Composable
fun MyAuthNavGraph(profileViewModel: ProfileViewModel) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val navController = rememberNavController()
    AnimatedNavHost(navController = navController, startDestination = AuthNavGraph.SPLASH.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
        },
        exitTransition = { ->
            slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
        },
        popEnterTransition = { ->
            slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
        },
        popExitTransition = { ->
            slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
        }
    ) {
//        navigation(startDestination = "FoodieZone", route = "main"){
//            composable("FoodieZone") { backStackEntry ->
//                val parentEntry = remember(backStackEntry) {
//                    navController.getBackStackEntry(AuthNavGraph.HOME.route)
//                }
//                val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)
//                FoodieZoneScreen(navController, navController,foodieZoneViewModel)
//            }
//
//
//            composable("Categories") {
//                val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel()
//                val foodCategoryViewModel: FoodCategoryViewModel = hiltViewModel()
//                //   CategoriesScreen(bottomNavController, foodieZoneViewModel)
//                FoodCategoryScreen(
//                    navController,
//                    navController,
//                    foodieZoneViewModel,
//                    foodCategoryViewModel
//                )
//            }
//
//            composable("Address") {
//                val addressViewModel: AddressViewModel = hiltViewModel()
//                AddressScreen(navController, addressViewModel) }
//            composable("MyOrders") {
//                MyOrders(navController, orderViewModel) }
//        }


        composable(route = AuthNavGraph.SPLASH.route) {
            SplashScreen(navController)
        }

        composable(route = AuthNavGraph.HOME.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }
            val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)
            HomeScreen(navController, foodieZoneViewModel, orderViewModel)
        }

        composable(route = AuthNavGraph.STORE.route) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }

            val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)
            StoreScreen(navController, foodieZoneViewModel, orderViewModel)
        }

        composable(
            route = AuthNavGraph.FOOD_LIST.route, arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->

            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }

            val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)
            val foodListViewModel: FoodListViewModel = hiltViewModel()
            FoodListScreen(navController, foodieZoneViewModel, foodListViewModel, categoryId)
        }

        composable(route = AuthNavGraph.ORDER.route) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }

            val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)

            OrderScreen(navController, foodieZoneViewModel, orderViewModel)
        }

        composable(route = AuthNavGraph.ORDER_CONFIRM.route) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }

            val foodieZoneViewModel: FoodieZoneViewModel = hiltViewModel(parentEntry)
            OrderSuccessScreen(navController, foodieZoneViewModel)
        }

        composable(route = AuthNavGraph.PROFILE.route) { navBackStackEntry ->

            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(AuthNavGraph.HOME.route)
            }
         //   val profileViewModel: ProfileViewModel = hiltViewModel(parentEntry)
            ProfileScreen(navController, profileViewModel)
        }

        composable(route = AuthNavGraph.ABOUT.route) { navBackStackEntry ->
            AboutScreen(navController)
        }

        composable(AuthNavGraph.MY_ORDERS.route) {
            MyOrders(navController, orderViewModel, true)
        }

        composable(AuthNavGraph.ADDRESS.route) {
            val addressViewModel: AddressViewModel = hiltViewModel()
            AddressScreen(navController, addressViewModel, true)
        }

        composable(AuthNavGraph.NOTIFICATION_LIST.route) {
            NotificationScreen(navController)
        }


        composable(AuthNavGraph.USEPROFILE.route) {
            ProfileUserScreen(navController)
        }

    }




}