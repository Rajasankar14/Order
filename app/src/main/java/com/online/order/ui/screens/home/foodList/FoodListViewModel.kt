package com.online.order.ui.screens.home.foodList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.online.order.data.Food
import com.online.order.data.FoodCategory
import com.online.order.data.SubFoodCategory
import com.online.order.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel  @Inject constructor(foodRepository: FoodRepository): ViewModel() {

    var mainFoodList = foodRepository.mainFoodList

    var foodItemCategory = foodRepository.foodItemCategory

    var foodCategoryList = foodRepository.foodCategory


        private val _finalFoodList = MutableStateFlow<List<Food>>(emptyList())
        val finalFoodList: StateFlow<List<Food>> get() = _finalFoodList



    private val _selectedSubCategoryList = MutableStateFlow<List<SubFoodCategory>>(emptyList())
    val selectedSubCategoryList: StateFlow<List<SubFoodCategory>> get() = _selectedSubCategoryList


    private  val _selectedCategoryName = MutableStateFlow<String>("")
    val selectedCategoryName : StateFlow<String> get() = _selectedCategoryName


    private val _filteredFoodList = MutableStateFlow<List<Food>>(emptyList())

    val filteredFoodList: StateFlow<List<Food>> = _filteredFoodList



    /*Food List*/

    var subCategorySelected : Int = 1


    fun prepareSubCategory(catId: Int){
        _selectedSubCategoryList.value = foodItemCategory.filter {
            it.categoryId == catId
        }
    }


    fun getCategoryName(catId: Int) {
        val categoryItem = foodCategoryList.find {
            it.categoryId == catId
        }

        if (categoryItem != null) {
            _selectedCategoryName.value = categoryItem.categoryName
        }else{
            _selectedCategoryName.value =  ""
        }
    }



    fun prepareFoodList(catID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val filteredByCategory  = mainFoodList.filter {
                it.categoryId == catID
            }

            withContext(Dispatchers.Main){
                _filteredFoodList.value = filteredByCategory
                Log.d("SUBB SIZE", "mainFoodList size: ${mainFoodList.size}")
                Log.d("SUBB CAT", "CAt size: ${_filteredFoodList.value.size}")
                if(filteredByCategory.isNotEmpty()){
                    Log.d("SUBB CATEGORY ID is",filteredByCategory[0].subCategoryId.toString())
                    subCategorySelected = filteredByCategory[0].subCategoryId
                    clearAndAddNewSubFoodFilterList(filteredByCategory[0].subCategoryId)
                }else{
                    Log.d("SUBB CATEGORY ID is","EMPTY")
                }
            }



        }
    }

    fun clearAndAddNewSubFoodFilterList(subCategoryId :Int){
        Log.d("SUBB CATEGORY ID passed",subCategoryId.toString())
        val finalFiltered  = mainFoodList.filter {
            it.subCategoryId == subCategoryId
        }
        _finalFoodList.value = finalFiltered
    }





}