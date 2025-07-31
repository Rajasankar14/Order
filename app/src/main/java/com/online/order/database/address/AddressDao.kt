package com.online.order.database.address

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Query("SELECT * FROM AddressBook")
    fun getAllAddresses(): Flow<List<AddressEntity>>

    @Query("Delete FROM AddressBook WHERE id = :id")
    suspend fun deleteAddress(id: Int)

    @Query("SELECT * FROM AddressBook WHERE id = :id")
    suspend fun editAddress(id: Int) : AddressEntity?

     @Update
    suspend fun updateAddress(address: AddressEntity)

    @Query("SELECT * FROM AddressBook ORDER BY id DESC LIMIT 1")
     fun getLastAddedAddress() : Flow<AddressEntity?>

    @Query("SELECT * FROM AddressBook WHERE id = :id LIMIT 1")
    fun getSelectedAddress(id : Int) : Flow<AddressEntity?>

}