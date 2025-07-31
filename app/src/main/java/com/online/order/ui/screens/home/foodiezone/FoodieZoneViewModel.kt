package com.online.order.ui.screens.home.foodiezone

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.online.order.data.Food
import com.online.order.data.FoodCategory
import com.online.order.data.Store
import com.online.order.data.SubFoodCategory
import com.online.order.data.repository.FilterRepository
import com.online.order.data.repository.FoodRepository
import com.online.order.data.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FoodieZoneViewModel @Inject constructor(
    foodRepository: FoodRepository,
    filterRepository: FilterRepository,
    storeRepository: StoreRepository
) : ViewModel() {
    var mainFoodList = foodRepository.mainFoodList
    var foodCategoryList = foodRepository.foodCategory



    var filterList = filterRepository.filterList

    var filterItemList = filterRepository.foodItemFilterList

    var storeFilterList = filterRepository.storeFilterList

    var selectedFoodItem: Food? by mutableStateOf(null)

    var foodCatSelected: Int = 1


    var foodCategoryId: Int = 0


    var storeList = storeRepository.storeList




    fun getStoreDetails(): Store? {
        if (selectedFoodItem == null) {
            Log.d("StoreScreenn", "selectedFoodItem is null")

        }
        val storeData: Store? = storeList.find { it.storeId == selectedFoodItem?.storeId }
        return storeData
    }

    fun getStoreFood(storeId: Int): List<Food> {
        val storeFoodList: List<Food> = mainFoodList.filter { it.storeId == storeId }
        return storeFoodList
    }


    fun createImageFile(context: Context) : File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}