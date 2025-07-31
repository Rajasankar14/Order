package com.online.order.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.online.order.R
import com.online.order.data.Food
import com.online.order.data.FoodCategory
import com.online.order.data.SubFoodCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor() {
    private val _mainFoodList = mutableStateListOf<Food>()
    var mainFoodList: List<Food> = _mainFoodList

    private val _foodCategory = mutableStateListOf<FoodCategory>()
    var foodCategory: List<FoodCategory> = _foodCategory


    private val _foodItemCategory = mutableStateListOf<SubFoodCategory>()
    var foodItemCategory: List<SubFoodCategory> = _foodItemCategory


    init {
        _mainFoodList.addAll(
            listOf(
                Food(
                    "Chicken Curry",
                    R.drawable.chickencurry,
                    "The Good Bowl",
                    "4.8",
                    "",
                    "30% OFF up to ₹75", true, 10, 32,
                    199, 1, "₹",
                    1,
                    0.8f,
                    "A wholesome combination with succelent chicken cooked in to flavour packed with pepper masala and spicy , tangy chicken, flavoured with curly leaves,ginger and garlic.",
                    1,
                    true,
                    listOf(R.drawable.chickencurry,R.drawable.chickencurry,R.drawable.chickencurry)

                ),
                Food(
                    "Mutton Curry",
                    R.drawable.muttoncurry1,
                    "The Good Bowl",
                    "4.5",
                    "",
                    "Get selected items @ ₹199 only", true, 10, 33, 250, 2, "₹", 1,
                    0.9f,
                    "A dish made of spiced mutton meatballs, deep fried to perfection",
                    1,
                    true,
                    listOf(R.drawable.muttoncurry1,R.drawable.muttoncurry1,R.drawable.muttoncurry1)

                ),
                Food(
                    "Kothu Protta",
                    R.drawable.kothu,
                    "The Sultan Hotel",
                    "4.5",
                    "",
                    "Flat ₹125 OFF above  ₹349", false, 10, 34, 120, 4, "₹", 2,
                    0.5f,
                    "Shredded paratta stir fried with spiced eggs, creating savoury, satisfying meal.",
                    1,
                    true,
                    listOf(R.drawable.kothu,R.drawable.kothu,R.drawable.kothu)


                ),
                Food(
                    "Chicken Kebab Biryani",
                    R.drawable.kebab_briyani,
                    "Thalaiva Biryani",
                    "4.3",
                    "",
                    "Flat ₹100 OFF above ₹249", false, 5, 1, 260, 5, "₹", 3,
                    0.9f,
                    "A popular biryani made with chicken, spiced to perfection and served with fragrant rice",
                    1,
                    true,
                    listOf(R.drawable.kebab_briyani,R.drawable.kebab_briyani,R.drawable.kebab_briyani)

                ),
                Food(
                    "Chicken Biryani",
                    R.drawable.chicken,
                    "Thalaiva Biryani",
                    "4.0",
                    "",
                    "Flat ₹125 OFF above  ₹199", true, 5, 2, 200, 6, "₹", 3,
                    0.8f,
                    "A traditional South indian Chicken biryani with country chicken, slow cooked in a spicy, flavourful ingredients and enjoy with fresh onion and chicken  peas",
                    1,
                    true,
                    listOf(R.drawable.chicken,R.drawable.chicken,R.drawable.chicken)

                ),
                Food(
                    "Mutton Biryani",
                    R.drawable.mutton_briyani,
                    "The Biryani zone",
                    "4.0",
                    "",
                    "30% OFF up to ₹75", true, 5, 3, 300, 7, "₹", 4,
                    0.6f,
                    "A dish made of spiced mutton meatballs, deep fried to perfection",
                    1,
                    true,
                    listOf(R.drawable.mutton_briyani,R.drawable.mutton_briyani,R.drawable.mutton_briyani)

                ),
                Food(
                    "Chicken Biryani",
                    R.drawable.chicken,
                    "12 mani Biryani",
                    "3.9",
                    "",
                    "Get 25% OFF on select items", true, 5, 2, 199, 8, "₹", 5,
                    0.5f,
                    "A flavourful biryani made with tender chicken, slow cooked in a spicy, flavourful ingredients and enjoy with fresh onion, cooked with fragrant rice and blend of aromatic spices.",
                    1,
                    true,
                    listOf(R.drawable.ic_biryani_l1,R.drawable.ic_biryani_chicken_l2,R.drawable.ic_biryani_chicken_l3)

                ),
                Food(
                    "Veg Meals",
                    R.drawable.vegmeals_small,
                    "Sabarees",
                    "4.5",
                    "",
                    "Get 5% offer for Rice Items",
                    true,
                    9, 29, 90, 9, "₹", 6,
                    0.9f,
                    "Rice, Sambar, Rasam, Kootu, poriyal,kolambu,butter milk, paayasam,appalam and leaf. Have a World Favourite South Indian traditional food with your friends and family",
                    0,
                    false,
                    listOf(R.drawable.vegmeals_small,R.drawable.vegmeals_small,R.drawable.vegmeals_small)

                ),
                Food(
                    "Bucket Chicken Biryani",
                    R.drawable.bucket_briyani,
                    "12 mani biryani",
                    "4.4",
                    "",
                    "Get 20% OFF Offer",
                    false,
                    5,
                    4,
                    599,
                    10,
                    "₹",
                    1,
                    0.9f,
                    "Freshly cooked dum biryani, cooked with succulent chicken pieces, maintained in exotic blend handpicked special  spices, finest long grain Basmati rice with brinjal and onion raita.",
                    1,
                    true,
                    listOf(R.drawable.bucket_briyani,R.drawable.bucket_briyani,R.drawable.bucket_briyani)
                ),
                Food(
                    "Curd Rice", R.drawable.curd_rice, "Sri Sai Bhavan", "4.8", "",
                    "Get 5% offer for Rice Items", false, 9, 30, 60, 11, "₹", 6,
                    0.7f,
                    "A dish made of spiced mutton meatballs, deep fried to perfection",
                    1,
                    true,
                    listOf(R.drawable.curd_rice,R.drawable.curd_rice,R.drawable.curd_rice)
                ),
                Food(
                    "Lemon rice", R.drawable.lemon_rice, "Gowri Mess", "3.9", "",
                    "Get 5% offer for Rice Items", false, 9, 31, 50, 12, "₹", 6,
                    0.7f,
                    "A dish made of spiced mutton meatballs, deep fried to perfection",
                    1,
                    true,
                    listOf(R.drawable.lemon_rice,R.drawable.lemon_rice,R.drawable.lemon_rice)
                ),
                Food(
                    "Vanilla Ice cream",
                    R.drawable.cupice,
                    "Arun Ice cream",
                    "4.8",
                    "3.5KM",
                    "Get 10 Rupees Offer on above 150 Rupees purchase", false, 2, 8, 70, 13, "₹", 7,
                    0.9f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.cupice,R.drawable.cupice,R.drawable.cupice)


                ),
                Food(
                    "StrawBerry Ice cream",
                    R.drawable.stawberryice,
                    "Bilal Ice cream Corner",
                    "4.8",
                    "3.5KM",
                    "Get 20% Offer on above 200 Rupees  purchase", true, 2, 9, 80, 14, "₹", 8,
                    0.6f,
                    "",
                    1,
                    true,listOf(R.drawable.stawberryice,R.drawable.stawberryice,R.drawable.stawberryice)


                ),
                Food(
                    "Chocolate Cup Ice cream",
                    R.drawable.chococupice,
                    "Ibbacco Ice cream",
                    "4.8",
                    "3.5KM",
                    "Get 18% Offer on above 200 Rupees  purchase", true, 2, 10, 120, 15, "₹", 9,
                    0.8f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.chococupice,R.drawable.chococupice,R.drawable.chococupice)

                ),
                Food(
                    "ButterScotch Family Ice cream",
                    R.drawable.buttorscotchfamily,
                    "Arun Ice Creams",
                    "4.8",
                    "3.5KM",
                    "Get 5% Offer on above 100 Rupees purchase", false, 2, 11, 300, 16, "₹", 7,
                    0.7f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.buttorscotchfamily,R.drawable.buttorscotchfamily,R.drawable.buttorscotchfamily)

                ),
                Food(
                    "kesar Pista Ice cream",
                    R.drawable.kesarpistafamily,
                    "Arun Ice Creams",
                    "4.8",
                    "3.5KM",
                    "Get 20% Offer on above 560 Rupees purchase", false, 2, 12, 280, 17, "₹", 7,
                    0.4f,
                    "",
                    1,
                    true
                    ,listOf(R.drawable.kesarpistafamily,R.drawable.kesarpistafamily,R.drawable.kesarpistafamily)

                ),
                Food(
                    "Pepsi 330 ml",
                    R.drawable.pepsi1,
                    "PEPSI",
                    "4.8",
                    "3.5KM",
                    "Buy 2 Pepsi and get 1 Pepsi Free", false, 3, 13, 90, 18, "₹", 10,
                    0.8f,
                    "",
                    1,
                    true
                    ,listOf(R.drawable.pepsi1,R.drawable.pepsi1,R.drawable.pepsi1)

                ),
                Food(
                    "Pepsi 750 ml",
                    R.drawable.pepsi2,
                    "PEPSI",
                    "4.2",
                    "2.5KM",
                    "Buy 2 Pepsi and get 10% offer", false, 3, 14, 150, 19, "₹", 10,
                    0.7f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.pepsi2,R.drawable.pepsi2,R.drawable.pepsi2)

                ),
                Food(
                    "Coca-Cola 330 ml",
                    R.drawable.coke1,
                    "COCA-COLA",
                    "4.2",
                    "2.5KM",
                    "Buy 3 Pepsi and get 35% Offer", false, 3, 15, 90, 20, "₹", 10,
                    0.9f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.coke1,R.drawable.coke1,R.drawable.coke1)

                ),
                Food(
                    "Coca-Cola 750 ml",
                    R.drawable.coke2,
                    "COCA-COLA",
                    "4.0",
                    "1.8KM",
                    "Buy 1 Coca-Cola and get 10% Offer", false, 3, 16, 200, 21, "₹", 10,
                    0.7f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.coke2,R.drawable.coke2,R.drawable.coke2)

                ),
                Food(
                    "Mountain Dew 330 ml",
                    R.drawable.md1,
                    "MOUNTAIN-DEW",
                    "4.9",
                    "1.8KM",
                    "Buy 4 Coca-Cola and get 15% Offer", false, 3, 17, 80, 22, "₹", 10,
                    0.4f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.md1,R.drawable.md1,R.drawable.md1)

                ),
                Food(
                    "Mountain Dew 750 ml",
                    R.drawable.md2,
                    "MOUNTAIN-DEW",
                    "4.0",
                    "1.3KM",
                    "Buy 2 Mountain Dew and get 10% Offer", false, 3, 18, 160, 23, "₹", 10,
                    0.5f,
                    "",
                    1,
                    true,
                    listOf(R.drawable.md2,R.drawable.md2,R.drawable.md2)

                ),
                Food(
                    "Black Forest Mini Cake",
                    R.drawable.blackforestcake,
                    "CAKE ZONE",
                    "4.0",
                    "1.3KM",
                    "Buy 2 Cakes and Get 1 Cake Free", false, 4, 19, 70, 24, "₹", 11,
                    0.8f,
                    "A classic yet everyone's favourite.Layers of black forest Ice cream with chocolate sponge cake, tapped with dark.Do invite it to your next priority.",
                    2,
                    false,
                    listOf(R.drawable.blackforestcake,R.drawable.blackforestcake,R.drawable.blackforestcake)

                ),
                Food(
                    "Red velvet Mini Cake",
                    R.drawable.redvelvetcake,
                    "CAKE ZONE",
                    "4.0",
                    "1.3KM",
                    "For Each 1 Cake and Get 10% Offer", false, 4, 20, 110, 25, "₹", 11,
                    0.6f,
                    "Red velvet ice cream wrapped in red velvet morble cake & white chockalte truffle, drizzled with cream cheese frosting.this white chockablock and red velvet cake will have dreaming of christmas!",
                    2,
                    false,
                    listOf(R.drawable.redvelvetcake,R.drawable.redvelvetcake,R.drawable.redvelvetcake)

                ),
                Food(
                    "Kids BirthDay Cake",
                    R.drawable.birthdaycakes,
                    "Jeyaram Bakery",
                    "4.0",
                    "1.3KM",
                    "Buy BirthDay Cake and get Discount of 29 Rupees",
                    false,
                    4,
                    21,
                    140,
                    26,
                    "₹",
                    12,
                    0.9f,
                    "Delve into the rich, sinful chocolate cake! Coated with ganache made from overture Chocolate & fresh cream and overall texture with bitter sweet filling.",
                    2,
                    false,
                    listOf(R.drawable.birthdaycakes,R.drawable.birthdaycakes,R.drawable.birthdaycakes)

                ),
                Food(
                    "Birthday cake (1/2 kg) ",
                    R.drawable.happybirthday2,
                    "Harish Bakery",
                    "4.0",
                    "1.3KM",
                    "Buy BirthDay Cake and Get 10% Discount", false, 4, 24, 160, 27, "₹", 12,
                    0.6f,
                    "Soft vanilla spong layered with the sweetness of butterscotch flavoured whipped cream is sure to make you fall in love with this cake.",
                    2,
                    false,
                    listOf(R.drawable.happybirthday2,R.drawable.happybirthday2,R.drawable.happybirthday2)

                ),
                Food(
                    "Egg less Cake",
                    R.drawable.egglesscake,
                    "Kiran Cake shop",
                    "4.0",
                    "1.3KM",
                    "Get 10% Discount for Buying 2 Cakes", false, 4, 22, 100, 27, "₹", 13,
                    0.5f,
                    "(Egg less) Our Hazelnut Chocolate Cake us soft ,made with rich chockablock and with a tingle of hazelnut, making it sinfully divine! and sure is sure to impress all!",
                    0,
                    false,
                    listOf(R.drawable.egglesscake,R.drawable.egglesscake,R.drawable.egglesscake)

                ),
                Food(
                    "Birth Day Cake (1 kg)",
                    R.drawable.happybirthday2,
                    "Kiran Cake shop",
                    "4.0",
                    "1.3KM",
                    "Buy BirthDay Cake and Get 10% Discount", false, 4, 23, 240, 28, "₹", 14,
                    0.95f,
                    "Delve in to this rich, sinfully Chocolate Cake! Coated with ganache made from overture Chocolate & fresh cream and overall texture with bitter sweet filling.",
                    2,
                    false,
                    listOf(R.drawable.happybirthday2,R.drawable.happybirthday2,R.drawable.happybirthday2)

                ),

                Food(
                    "Veg Cheese Burger",
                    R.drawable.vegburger,
                    "Burger King",
                    "4.0",
                    "1.3KM",
                    "Buy Veg Cheese Burger and Get 10% Discount", false, 6, 25, 259, 29, "₹", 15,
                    0.7f,
                    "2 layers of classic veg patties and base with lettuce, onion rings and veggies with 3 varieties of cheese and signature sauces.",
                    0,
                    false,
                    listOf(R.drawable.vegburger,R.drawable.vegburger,R.drawable.vegburger)

                ),

                Food(
                    "Chicken Burger",
                    R.drawable.chickenburger,
                    "KFC",
                    "4.0",
                    "1.3KM",
                    "Buy 2 Burger and Get 12% Discount", false, 6, 26, 359, 30, "₹", 16,
                    0.9f,
                    "Crispy veg patty, cajun maya, cajun onion rings & jalapenos between butterfly brioche buns & Burst of  cheesy flavour with cheese filled party, layered over a cruncy Cajun patty, lettuce and mayo inside the sesame buns.",
                    1,
                    false,
                    listOf(R.drawable.chickenburger,R.drawable.chickenburger,R.drawable.chickenburger)

                ),

                Food(
                    "Meat Burger",
                    R.drawable.meatburger,
                    "LJ Beef zone",
                    "4.0",
                    "1.3KM",
                    "Buy 1 Burger and Get 10% Discount", false, 6, 27, 345, 31, "₹", 17,
                    0.7f,
                    "Lettuce, onion and classic chicken patties (2 picese) and one type of cheese and our signature sauce.",
                    1,
                    true,
                    listOf(R.drawable.meatburger,R.drawable.meatburger,R.drawable.meatburger)


                ),
                Food(
                    "Chicken Crispy Burger",
                    R.drawable.crispychickernburger,
                    "Arun's Burger shop",
                    "4.0",
                    "1.3KM",
                    "Buy 1 Burger and Get Flat 50 Rupees discount", false, 6, 28, 299, 32, "₹", 18,
                    0.9f,
                    "One classic chicken patty, cheese liquid and our signature source base with lettuce oninon  nad chicken rings",
                    1,
                    true,
                    listOf(R.drawable.crispychickernburger,R.drawable.crispychickernburger,R.drawable.crispychickernburger)

                ),
            )
        )


        _foodCategory.addAll(
            listOf(
                FoodCategory(1, "Popular", R.drawable.ic_fav),
                FoodCategory(2, "Ice Creams", R.drawable.ic_strawberry_category),
                FoodCategory(3, "Soft Drinks", R.drawable.pepsi2),
                FoodCategory(4, "Cakes", R.drawable.ic_cake_category),
                FoodCategory(5, "Biryani", R.drawable.ic_chicken_biryani),
                FoodCategory(6, "Burgers", R.drawable.ic_burger_category),
               // FoodCategory(7, "Pizza", R.drawable.ic_pizza_category),
               // FoodCategory(8, "Snacks", R.drawable.ic_scanks),
                FoodCategory(9, "Veg Items", R.drawable.ic_burger_category),
                FoodCategory(10, "Non Veg Curry", R.drawable.curry)
            )
        )

        _foodItemCategory.addAll(
            listOf(
                SubFoodCategory(5, 1, "Chicken Kebab Biryani", R.drawable.ic_biryani_gen),
                SubFoodCategory(5, 2, "Chicken Biryani", R.drawable.ic_chicken_biryani),
                SubFoodCategory(5, 3, "Mutton Biryani", R.drawable.ic_mutton_biryani),
                SubFoodCategory(5, 4, "Bucket Biryani", R.drawable.ic_bucket_bir_category),
                SubFoodCategory(5, 5, "Veg Biryani", R.drawable.ic_veg_biryani),
                SubFoodCategory(5, 6, "Egg Biryani", R.drawable.ic_egg_biryani),
                SubFoodCategory(5, 7, "Mushroom Biryani", R.drawable.ic_mushroom),
                SubFoodCategory(2, 8, "Vanilla IceCream", R.drawable.ic_vanilla_category),
                SubFoodCategory(2, 9, "Strawberry IceCream", R.drawable.ic_strawberry_category),
                SubFoodCategory(2, 10, "Chocolate IceCream", R.drawable.ic_chocklate_category),
                SubFoodCategory(2, 11, "ButterScotch IceCream", R.drawable.ic_butterscotch_category),
                SubFoodCategory(2, 12, "Kesar Pista IceCream", R.drawable.ic_kesar_pistha_category),
                SubFoodCategory(3, 13, "Mini Pepsi", R.drawable.pepsi1),
                SubFoodCategory(3, 14, "Large Pepsi", R.drawable.pepsi2),
                SubFoodCategory(3, 15, "Mini Coke", R.drawable.coke1),
                SubFoodCategory(3, 16, "Large Coke", R.drawable.coke2),
                SubFoodCategory(3, 17, "Mountain Dew Mini", R.drawable.md1),
                SubFoodCategory(3, 18, "Mountain Dew", R.drawable.md2),
                SubFoodCategory(4, 19, "Black Forest Cake", R.drawable.blackforestcake),
                SubFoodCategory(4, 20, "Red Velvet Cake", R.drawable.redvelvetcake),
                SubFoodCategory(4, 21, "kids birthday Cake", R.drawable.birthdaycakes),
                SubFoodCategory(4, 22, "Eggless Cake", R.drawable.egglesscake),
                SubFoodCategory(4, 23, "BirthDay 1kg Cake", R.drawable.birthdaycake),
                SubFoodCategory(4, 24, "BirthDay 1kg Cake", R.drawable.happybirthday2),
                SubFoodCategory(6, 25, "Veg Cheese Burger", R.drawable.vegburger),
                SubFoodCategory(6, 26, "Chicken Burger", R.drawable.chickenburger),
                SubFoodCategory(6, 27, "Meat Burger", R.drawable.meatburger),
                SubFoodCategory(6, 28, "Chicken crispy Burger", R.drawable.crispychickernburger),
                SubFoodCategory(9, 29, "Veg Meals", R.drawable.vegmeals_small),
                SubFoodCategory(9, 30, "Curd Rice", R.drawable.curd_rice),
                SubFoodCategory(9, 31, "Lemon Rice", R.drawable.lemon_rice),
                        SubFoodCategory(10, 32, "Chicken Curry", R.drawable.curry),
            SubFoodCategory(10, 33, "Mutton Curry", R.drawable.mutton),
            SubFoodCategory(10, 34, "Kothu Prota", R.drawable.kothu)

            )
        )
    }


}
