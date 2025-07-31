package com.online.order.database.myorderDetail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(myOrderDetail: MyOrderDetail)

    @Query("SELECT * from MyOrderDetail where orderId = :orderId")
    fun getOrderDetail(orderId : String) : Flow<List<MyOrderDetail>>
}