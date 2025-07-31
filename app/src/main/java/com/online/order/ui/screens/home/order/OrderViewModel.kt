package com.online.order.ui.screens.home.order

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.online.order.R
import com.online.order.data.Food
import com.online.order.data.Store
import com.online.order.data.repository.StoreRepository
import com.online.order.database.address.AddressDao
import com.online.order.database.address.AddressEntity
import com.online.order.database.myorder.MyOrders
import com.online.order.database.myorder.MyOrdersDao
import com.online.order.database.myorderDetail.MyOrderDetail
import com.online.order.database.myorderDetail.MyOrderWithDetails
import com.online.order.database.myorderDetail.OrderDetailDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(storeRepository: StoreRepository, private val ordersDao: MyOrdersDao, private val orderDetailDao: OrderDetailDao,
    private val addressDao: AddressDao
) :ViewModel() {
    var orderedMapList =  mutableStateMapOf<Int, MutableList<Food>>()

    var processOrderMapList =  mutableStateMapOf<Int, MutableList<Food>>()

    var myOrderList : StateFlow<List<MyOrderWithDetails>> = ordersDao.getOrders().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

     var myLastOrder : StateFlow<MyOrders?> = ordersDao.getLastOrder().stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),null)

    var storeList = storeRepository.storeList


    /*For Address*/
    var lastAddedAddress  : StateFlow<AddressEntity?> = addressDao.getLastAddedAddress().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _updatedAddress  = MutableStateFlow<AddressEntity?>(null)
    val  updatedAddress : StateFlow<AddressEntity?> = _updatedAddress




    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data object NavigateToOrderConfirmation : UiEvent()
        data class BottomSheetState(val dismissBottomSheet: Boolean) : UiEvent()
    }
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun getOrderedMapListByStoreId(storeId: Int) : MutableMap<Int, MutableList<Food>>{
        return orderedMapList.filterValues {
                foodList ->
            foodList.any { it.storeId == storeId }

        }.mapValues { (_, foodList) ->
            foodList.filter { it.storeId == storeId }.toMutableList()
        }.toMutableMap()
    }



    fun addOrderedItem(item: Food) {
        orderedMapList.getOrPut(item.foodId){ mutableStateListOf() }.add(item)

    }

    fun removeOrderedItem(item: Food){
        orderedMapList[item.foodId]?.let { list ->
            if (list.isNotEmpty())   list.removeAt(list.size - 1)
            if (list.isEmpty()) orderedMapList.remove(item.foodId)

        }
    }

    fun getTotalOrderAmount(storeId: Int) : Int {
        var totalAmount = 0
        if (orderedMapList.size > 0) {
            for((_,foodList) in orderedMapList ){
                totalAmount += foodList.filter { it.storeId == storeId }.sumOf { it.price }
            }
            return totalAmount
        }else{
            return totalAmount
        }
    }


    fun getTotalOrderedItems(orderedListByStoreId : MutableMap<Int, MutableList<Food>>) : Int {
        var totalItems = 0
        if(orderedListByStoreId.isNotEmpty()){
            for((_, foodlist) in orderedListByStoreId){
                totalItems += foodlist.size
            }
            return totalItems
        }else{
            return totalItems
        }
    }

    fun orderedFoodList(orderedItem: MutableMap<Int, MutableList<Food>>) {
        var storeId = 0
        processOrderMapList.clear()
        for ((foodId, foodList) in orderedItem) {
             orderedMapList.remove(foodId)
            processOrderMapList[foodId] = foodList
            storeId = foodList[0].storeId
        }




        viewModelScope.launch {


            if(processOrderMapList.isNotEmpty()){
                if(storeId != 0){
                    val store : Store = storeList.filter { (storeId == it.storeId)  }[0]
                    val totalLineItems = HashMap(processOrderMapList.mapValues { it.value.toList() }).size
                    val orderId = generateOrderId()

                    val myCurrentOrder = MyOrders(0,orderId, R.drawable.crispychickernburger,store.storeName,store.storeLocation,totalLineItems, 0 )
                    ordersDao.insertMyOrder(myCurrentOrder)

                    processOrderMapList.forEach { (foodId, foodList) ->
                        val food = foodList[0]
                        val orderDetail = MyOrderDetail(
                            0,
                            orderId,
                            food.foodName,
                            food.foodImage,
                            food.distance,
                            food.offer,
                            food.categoryId,
                            food.subCategoryId,
                            food.price,
                            foodId,
                            food.priceCurrency,
                            food.storeId,
                            foodList.size,
                            0
                        )
                        orderDetailDao.insertOrderDetail(orderDetail)
                    }
                    _uiEvent.emit(UiEvent.NavigateToOrderConfirmation)

                }else{
                    _uiEvent.emit(UiEvent.ShowSnackBar("Store not Found"))
                }

            }else{
                _uiEvent.emit(UiEvent.ShowSnackBar("Order not Processed"))
            }
        }
    }

    private suspend fun generateOrderId() : String{
        val lastOrderId = ordersDao.getLastOrderNumber()

        if(lastOrderId.isNullOrEmpty()){
           return "ORDER001"
        }else{
            val lstNumber = lastOrderId.removePrefix("ORDER").toIntOrNull() ?: 0
            val nxtNumber = lstNumber+1
            return "ORDER$nxtNumber"


        }

    }

    suspend fun deleteOrder(orderId : String) {
        ordersDao.deleteOrder(orderId)
        _uiEvent.emit(UiEvent.ShowSnackBar("Order deleted Successfully"))
    }


    fun getSelectedAddress(addressId : Int){
        viewModelScope.launch {
            val address= addressDao.getSelectedAddress(addressId).firstOrNull()
            _updatedAddress.value = address
            if(address != null){
                Log.d("OrderrrrVieemdedl", "true")
                _uiEvent.emit(UiEvent.BottomSheetState(true))

            }else{
                Log.d("OrderrrrVieemdedl", "false")
            }
        }
    }


}