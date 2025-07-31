package com.online.order.data

data class FoodCategory(var categoryId : Int, var categoryName : String, var categoryImage : Int)

data class SubFoodCategory(var categoryId : Int, var subCategoryId : Int, var categoryName : String, var categoryImage : Int)
