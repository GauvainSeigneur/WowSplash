package com.seigneur.gauvain.wowsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seigneur.gauvain.wowsplash.data.model.token.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.utils.RoomConverter

@Database(entities = [AccessToken::class, User::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class WowSplashDataBase : RoomDatabase() {
     // DAO
    abstract fun mAccessTokenDao(): AccessTokenDao
    abstract fun mUserDao():UserDao
}


