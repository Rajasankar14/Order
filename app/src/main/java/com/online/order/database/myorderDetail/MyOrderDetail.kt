package com.online.order.database.myorderDetail

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.online.order.database.myorder.MyOrders

@Entity(tableName = "MyOrderDetail",
    foreignKeys = [ForeignKey(entity = MyOrders::class, parentColumns = ["orderId"], childColumns = ["orderId"], onDelete = ForeignKey.CASCADE)])
data class MyOrderDetail(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var orderId : String,
var foodName: String,
var foodImage: Int,
var distance: String,
var offer: String,
var categoryId: Int,
var subCategoryId: Int,
var price: Int,
var foodId : Int,
var priceCurrency: String,
var storeId : Int,
var foodType : Int,
var isSpicy : Int)