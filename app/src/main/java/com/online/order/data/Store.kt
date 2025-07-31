package com.online.order.data

data class Store(
    var storeId : Int,
    var storeName : String,
    var storeLocation : String,
    var storeOffers: List<Offers>,
    var storeDistance : String,
    var storeDelivery : String,
    var storeRating : String,
    var storeFacilities : List<StoreFacilities>,
    var storeFilters: List<Offers>,
    var storeOrderedFood: List<Food>,
    var pureVeg:Boolean,
)
