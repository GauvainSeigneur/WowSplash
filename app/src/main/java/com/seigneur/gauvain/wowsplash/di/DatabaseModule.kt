package com.seigneur.gauvain.wowsplash.di

import androidx.room.Room
import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

    //Single module
    val databaseModule =module {
        single {
            Room.databaseBuilder(androidApplication(), WowSplashDataBase::class.java, "wowsplash-db")
                .fallbackToDestructiveMigration()
                .build()
        }

        // TaskDAO instance (get instance from WowSplashDataBase)
        single { get<WowSplashDataBase>().mAccessTokenDao() }
    }






