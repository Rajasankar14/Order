package com.online.order.database.myorder

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "MyOrders", indices = [Index(value = ["orderId"], unique = true)])
data class MyOrders(@PrimaryKey(autoGenerate = true)
                    val id: Int = 0,
                    val orderId : String,
                    val storeImageResId : Int = 0,
                    val storeName : String,
                    val storeLocation : String,
                    val totalLineCount : Int,
                    val orderStatus : Int,

    ) {
}




