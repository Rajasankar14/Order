package com.online.order.database.myorder

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.online.order.database.myorderDetail.MyOrderWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MyOrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyOrder(myOrders: MyOrders)

    @Query("SELECT * FROM MyOrders ORDER BY id DESC ")
     fun getOrders() : Flow<List<MyOrderWithDetails>>

    @Query("SELECT * FROM MyOrders ORDER BY id DESC LIMIT 1")
    fun getLastOrder() : Flow<MyOrders>

    @Query("SELECT orderId FROM MyOrders ORDER BY id DESC LIMIT 1")
    suspend fun getLastOrderNumber() : String?

    @Query("DELETE FROM MyOrders where orderId = :orderId")
    suspend fun deleteOrder(orderId : String)
}