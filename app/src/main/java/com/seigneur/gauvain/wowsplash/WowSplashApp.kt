package com.seigneur.gauvain.wowsplash

import android.app.Application
import com.seigneur.gauvain.wowsplash.di.remoteDataSourceModule
import com.seigneur.gauvain.wowsplash.di.repositoryModule
import com.seigneur.gauvain.wowsplash.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber

class WowSplashApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            Timber.d("start koin lol")
            // Android context
            androidContext(this@WowSplashApp)
            // modules
            modules(listOf(remoteDataSourceModule, repositoryModule, viewModelModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}