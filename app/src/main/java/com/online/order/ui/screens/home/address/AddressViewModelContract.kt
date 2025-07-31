package com.online.order.ui.screens.home.address

import com.online.order.data.Filter
import com.online.order.database.address.AddressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AddressViewModelContract {
    val addressList: StateFlow<List<AddressEntity>>
    val locationType : StateFlow<List<Filter>>
    val editAddress : StateFlow<AddressEntity?>
    var dismissBottomSheet : SharedFlow<Boolean>

    fun insertAddress(address: AddressEntity)
    fun deleteAddress(address: AddressEntity)
    fun getAddressById(address: AddressEntity)
    fun updateAddress(address: AddressEntity)
    fun resetEditAddress()
}