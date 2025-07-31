package com.online.order.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


data class Food(
    var foodName: String,
    var foodImage: Int,
    var hotelName: String,
    val rating: String,
    var distance: String,
    var offer: String,
    var popular: Boolean = false,
    var categoryId: Int,
    var subCategoryId: Int,
    var price: Int,
    var foodId : Int,
    var priceCurrency: String,
    var storeId : Int,
    var highlyRecommended : Float,
    var foodDescription: String,
    var foodType : Int,
    var isSpicy : Boolean,
    var largeImageList : List<Int>
){
    var orderedQty by mutableIntStateOf(0)
}
