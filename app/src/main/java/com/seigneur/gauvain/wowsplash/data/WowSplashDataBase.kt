package com.seigneur.gauvain.wowsplash.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.utils.RoomConverter


@Database(entities = [AccessToken::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class WowSplashDataBase : RoomDatabase() {
     // DAO
    abstract fun mAccessTokenDao(): AccessTokenDao
}


