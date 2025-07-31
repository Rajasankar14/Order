package com.online.order.ui.screens.home.foodCategory

import androidx.lifecycle.ViewModel
import com.online.order.data.FoodCategory
import com.online.order.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodCategoryViewModel @Inject constructor(foodRepository: FoodRepository) : ViewModel() {

    var foodCategoryList = foodRepository.foodCategory


    private val _foodSearchList = MutableStateFlow<List<FoodCategory>>(emptyList())
    val foodSearchList = _foodSearchList



    fun searchFoodList(searchFoodText: String) {
        foodSearchList.value = foodCategoryList.filter { it.categoryName.contains(searchFoodText, true) }

    }

    fun clearSearchFoodList() {
        foodSearchList.value = emptyList()

    }

    private val _recentSearchList = MutableStateFlow<List<FoodCategory>>(emptyList())
    val recentSearchList = _recentSearchList


    fun addRecentSearch(foodCategory: FoodCategory) {
        val isAlreadyExists =  _recentSearchList.value.any{
            it.categoryId == foodCategory.categoryId

        }
        if(!isAlreadyExists){
            _recentSearchList.value += foodCategory
        }

    }

    fun deleteRecentSearch(foodCategory: FoodCategory){
        _recentSearchList.value = _recentSearchList.value.filter { it != foodCategory }
    }


}