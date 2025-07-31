package com.online.order.database.myorderDetail

import androidx.room.Embedded
import androidx.room.Relation
import com.online.order.database.myorder.MyOrders

data class MyOrderWithDetails(
    @Embedded val orders : MyOrders,
    @Relation (
        parentColumn = "orderId",
        entityColumn = "orderId"
    )

    val orderDetails : List<MyOrderDetail>
)
