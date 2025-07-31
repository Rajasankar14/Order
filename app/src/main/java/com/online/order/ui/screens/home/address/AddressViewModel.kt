package com.online.order.ui.screens.home.address

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.online.order.R
import com.online.order.data.Filter
import com.online.order.database.address.AddressDao
import com.online.order.database.address.AddressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AddressViewModel @Inject constructor(private val addressDao: AddressDao) : ViewModel(),AddressViewModelContract {


    override val addressList: StateFlow<List<AddressEntity>> =
        addressDao.getAllAddresses().
    stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var addressSelected = mutableStateOf<AddressEntity?>(null)


    private var _locationType = MutableStateFlow<List<Filter>>(emptyList())
    override val locationType : StateFlow<List<Filter>>  get() = _locationType


    private var _editAddress = MutableStateFlow<AddressEntity?>(null)
    override val editAddress : StateFlow<AddressEntity?>  get() = _editAddress


    private val _dismissBottomSheet = MutableSharedFlow<Boolean>()
    override var dismissBottomSheet : SharedFlow<Boolean> = _dismissBottomSheet


    init {
        getAllData()
    }





    private fun getAllData(){
        _locationType.value = listOf(Filter("Home", R.drawable.ic_home), Filter("Work",  R.drawable.ic_office),
            Filter("Hotel", R.drawable.ic_hotel),
            Filter("Other", R.drawable.ic_park))
    }



    override fun insertAddress(address: AddressEntity) {
        viewModelScope.launch {
            try {
                addressDao.insertAddress(address)
                _dismissBottomSheet.emit(true)
            }catch (exception : Exception){
                _dismissBottomSheet.emit(false)

            }
        }
    }

    override fun deleteAddress(address: AddressEntity) {
        viewModelScope.launch {
            try {
                addressDao.deleteAddress(address.id)
            }catch (exception : Exception){

            }
        }

    }


    override fun getAddressById(address: AddressEntity) {
        viewModelScope.launch {
            _editAddress.value = addressDao.editAddress(address.id)
        }

    }

    override fun updateAddress(address: AddressEntity){
        viewModelScope.launch {
            try {
                addressDao.updateAddress(address)
                _dismissBottomSheet.emit(true)
                resetEditAddress()
            }catch (exception : Exception){
                _dismissBottomSheet.emit(false)
                resetEditAddress()
            }


        }
    }

    override fun resetEditAddress(){
        _editAddress.value = null

    }


}