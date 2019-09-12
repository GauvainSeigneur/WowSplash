package com.seigneur.gauvain.wowsplash

import android.app.Application
import com.seigneur.gauvain.wowsplash.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber

class WowSplashApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            Timber.d("start koin BasePagedListAdapter")
            // Android context
            androidContext(this@WowSplashApp)
            // modules
            modules(listOf(remoteDataSourceModule, repositoryModule, viewModelModule, databaseModule, tempRepositoryModule))
        }
        Timber.plant(Timber.DebugTree())
    }
}