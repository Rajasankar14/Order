package com.online.order.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.online.order.R
import com.online.order.data.Offers
import com.online.order.data.Store
import com.online.order.data.StoreFacilities
import javax.inject.Inject

class StoreRepository @Inject constructor() {
    private val _storeList = mutableStateListOf<Store>()
    var storeList: List<Store> = _storeList

    init {
        _storeList.addAll(
            listOf(
                Store(
                    1, "The Good Bowl", "Guindy", emptyList(), "1.8 km", "15-18 mins", "4.8",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), false
                ),
                Store(
                    2, "The Sultan Hotel", "velachery", emptyList(), "2.8 km", "35 mins", "4.6",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), false
                ),

                Store(
                    3,
                    "Thaliava Briyani",
                    "velachery Main road",
                    emptyList(),
                    "2.6 km",
                    "32 mins",
                    "4.5",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ),
                    emptyList(),
                    emptyList(),
                    false
                ),

                Store(
                    4, "The Briyani zone", "Tharamani", emptyList(), "2.6 km", "28 mins", "4.5",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), false
                ),

                Store(
                    5,
                    "12'o clock Briyani",
                    "Gandhi Nagar",
                    emptyList(),
                    "2.8 km",
                    "30 mins",
                    "4.4",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ),
                    emptyList(),
                    emptyList(),
                    false
                ),


                Store(
                    6, "Sabarees", "Guindy", emptyList(), "1.8 km", "15-18 mins", "4.8",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Curd Rice Lovers food", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Pure vegeterain", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Family", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),


                Store(
                    7, "Arun Ice creams", "Gandhi Nagar", emptyList(), "0.9 km", "10 mins", "4.9",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    8, "Bilal  Ice cream Corner", "Adayar", emptyList(), "3 km", "45 mins", "4.7",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    9,
                    "Ibacco  Ice cream",
                    "Ekkaduthangal",
                    emptyList(),
                    "2.4 km",
                    "31 mins",
                    "4.7",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ),
                    emptyList(),
                    emptyList(),
                    true
                ),
                Store(
                    10,
                    "Sheriff cool drinks corner",
                    "Guindy",
                    emptyList(),
                    "1.4 km",
                    "21 mins",
                    "4.9",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ),
                    emptyList(),
                    emptyList(),
                    true
                ),

                Store(
                    11, "Caze Zone", "SRP Tools", emptyList(), "0.9 km", "31 mins", "4.2",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    12, "Jeyaram Bakery", "Guindy", emptyList(), "1.8 km", "21 mins", "4.2",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Frequently reordered", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 50 orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    13, "Kiran Cake shop", "Thiruvanmayur", emptyList(), "2.2 km", "31 mins", "4.2",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Candle are free", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Extra toppings available", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Loved by Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    14, "Harish Bakes and Cakes", "Guindy", emptyList(), "1.8 km", "21 mins", "4.4",

                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "Favourite for Kids", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Always have coupons", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Served with Love", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),


                Store(
                    15, "Burger King", "Guindy", emptyList(), "1.8 km", "21 mins", "4.4",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "On time Delivery", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 100+ orders without any compliant", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "All time Favourite", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),

                Store(
                    16, "KFC", "Guindy", emptyList(), "1.8 km", "21 mins", "4.4",
                    listOf(
                        StoreFacilities(
                            1, "Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "All time tasty", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Recycle friendly", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Delivery partner", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    17, "LF Beef Zone", "Taramani", emptyList(), "2.6 km", "27 mins", "4.6",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "No Artifical ingredients", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Last 20+ orders within 3 days", R.drawable.tc_tick
                        )
                    ), emptyList(), emptyList(), true
                ),
                Store(
                    18,
                    "Arun's Burger shop",
                    "perungudi",
                    emptyList(),
                    "3.6 km",
                    "38 mins",
                    "4.2",
                    listOf(
                        StoreFacilities(
                            1, "No Packing Charges", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            2, "No Long time Waiting", R.drawable.tc_tick
                        ),
                        StoreFacilities(
                            3, "Positive Feedbacks", R.drawable.tc_tick
                        ), StoreFacilities(
                            4, "Instant Delivery", R.drawable.tc_tick
                        )
                    ),
                    emptyList(),
                    emptyList(),
                    true
                ),
            )

        )
    }
}