package com.online.order.di

import android.content.Context
import androidx.room.Room
import com.online.order.database.OfflineDatabase
import com.online.order.database.address.AddressDao
import com.online.order.database.myorder.MyOrdersDao
import com.online.order.database.myorderDetail.OrderDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OfflineDatabaseModule {

    @Provides
    @Singleton
    fun provideOfflineDatabase(@ApplicationContext context : Context) : OfflineDatabase {
        return Room
            .databaseBuilder(context, OfflineDatabase::class.java, "offlineDB")
            .addMigrations(OfflineDatabase.MIGRATION_1_2, OfflineDatabase.MIGRATION_2_3)
            .build()
    }

    @Provides
    fun providesAddressDao(offlineDatabase: OfflineDatabase) : AddressDao = offlineDatabase.addressDao()

    @Provides
    fun providesMyOrdersDao(offlineDatabase: OfflineDatabase) : MyOrdersDao = offlineDatabase.myOrdersDao()

    @Provides
    fun providesMyOrderDetailDao(offlineDatabase: OfflineDatabase) : OrderDetailDao = offlineDatabase.myOrderDetailDao()
}
