package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.repository.UserLocalDataProvider
import org.koin.dsl.module

val userLocalDataProviderModule = module {
    single {
        UserLocalDataProvider(get())
    }
}