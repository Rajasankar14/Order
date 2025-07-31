package com.online.order.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.online.order.R
import com.online.order.data.Filter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterRepository @Inject constructor()  {

    private val _filterList = mutableStateListOf<Filter>()

     val filterList get() = _filterList



    private val _foodItemFilterList = mutableStateListOf<Filter>()

    val foodItemFilterList get() = _foodItemFilterList

    private val _storeFilterList = mutableStateListOf<Filter>()
     val storeFilterList = _storeFilterList

    init {
        _filterList.addAll(
            listOf(
                Filter("Sort", R.drawable.ic_sort),
                Filter("Filter", R.drawable.ic_filter),
                Filter("Rated high", R.drawable.ic_rating),
                Filter("Nearby", R.drawable.ic_nearby),
                Filter("Price", R.drawable.ic_price),
                Filter("Quick Delivery", R.drawable.ic_quick)
            )
        )


        _foodItemFilterList.addAll(
            listOf(
                Filter("Filters", R.drawable.ic_filter),
                Filter("Paneer", R.drawable.ic_rating),
                Filter("Pan", R.drawable.ic_nearby),
                Filter("Medium", R.drawable.ic_price),
                Filter("Wheat Crust", R.drawable.ic_quick),
                Filter("Pesto", R.drawable.ic_quick),
                Filter("Under 300", R.drawable.ic_price),
                Filter("Great Offers", R.drawable.ic_quick),
                Filter("Rating 4.0+", R.drawable.ic_quick),
                        Filter("Pure Veg", R.drawable.ic_quick)

            )
        )

        _storeFilterList.addAll(
            listOf(
                    Filter("Filters", R.drawable.ic_filter_list),
                    Filter("Veg", R.drawable.ic_veg),
                    Filter("Egg", R.drawable.ic_egg),
                    Filter("Non Veg", R.drawable.ic_non_veg),
                    Filter("Highly reordered", R.drawable.ic_reset),
                    Filter("Quick Delivery", R.drawable.ic_quick),
                    Filter("Spicy", R.drawable.ic_chilly)
            )
        )

    }
}