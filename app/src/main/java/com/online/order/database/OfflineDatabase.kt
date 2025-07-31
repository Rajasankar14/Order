package com.online.order.database

import androidx.room.Database
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.online.order.database.address.AddressDao
import com.online.order.database.address.AddressEntity
import com.online.order.database.myorder.MyOrders
import com.online.order.database.myorder.MyOrdersDao
import com.online.order.database.myorderDetail.MyOrderDetail
import com.online.order.database.myorderDetail.OrderDetailDao

@Database(entities = [AddressEntity::class, MyOrders::class, MyOrderDetail::class], version = 3)
abstract class OfflineDatabase : RoomDatabase() {

    abstract fun addressDao() : AddressDao

    abstract  fun myOrdersDao() : MyOrdersDao

    abstract fun myOrderDetailDao() : OrderDetailDao


    companion object {
//        @Volatile
//        private var INSTANCE : OfflineDatabase? = null
//
//        fun getDatabase(context: Context) : OfflineDatabase{
//            return INSTANCE?: kotlin.synchronized(this){
//                val instance = Room
//                    .databaseBuilder(context.applicationContext, OfflineDatabase::class.java, "offlineDB")
//                    .addMigrations(MIGRATION_1_2)
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//
//        }

         val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(""" 
                CREATE TABLE IF NOT EXISTS `MyOrders` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `orderId` TEXT NOT NULL,
                'storeImageResId' INTEGER NOT NULL DEFAULT 0,
                `storeName` TEXT NOT NULL,
                `storeLocation` TEXT NOT NULL,
                `totalLineCount` INTEGER NOT NULL,
                `orderStatus` INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent())
            }

        }

        val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(""" 
                CREATE TABLE IF NOT EXISTS `MyOrderDetail` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `orderId` TEXT NOT NULL,
                'foodName' TEXT NOT NULL,
                `foodImage` INTEGER NOT NULL DEFAULT 0,
                'distance' TEXT NOT NULL,
                `offer` TEXT NOT NULL,
                `categoryId` INTEGER NOT NULL DEFAULT 0,
                `subCategoryId` INTEGER NOT NULL DEFAULT 0,
                `price` INTEGER NOT NULL DEFAULT 0,
                `foodId` INTEGER NOT NULL DEFAULT 0,
                'priceCurrency' TEXT NOT NULL,
                `storeId` INTEGER NOT NULL DEFAULT 0,
                `foodType` INTEGER NOT NULL DEFAULT 0,
                `isSpicy` INTEGER NOT NULL DEFAULT 0
                )
            """.trimIndent())
            }

        }
    }
}




