package com.seigneur.gauvain.wowsplash

import android.app.Application
import com.seigneur.gauvain.wowsplash.di.*
import com.seigneur.gauvain.wowsplash.di.logIn.logInModule
import com.seigneur.gauvain.wowsplash.di.photo.photoModule
import com.seigneur.gauvain.wowsplash.di.photoDetails.photoDetailsModule
import com.seigneur.gauvain.wowsplash.di.photoList.photoListModule
import com.seigneur.gauvain.wowsplash.di.user.userModule
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
            modules(listOf(
                remoteDataSourceModule,
                repositoryModule,
                viewModelModule,
                databaseModule,
                photoModule,
                photoListModule,
                userModule,
                logInModule,
                photoDetailsModule,
                photoDetailsDataProviderModule,
                photoListDataProviderModule
            ))
        }
        Timber.plant(Timber.DebugTree())
    }
}