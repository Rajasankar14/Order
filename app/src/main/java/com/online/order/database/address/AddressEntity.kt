package com.online.order.database.address

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddressBook")
data class AddressEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                         val name: String,
                         val phoneNumber: String,
                         val fullAddress: String,
                         val floor: String,
                         val landmark: String,
    val locationType : String) {

}