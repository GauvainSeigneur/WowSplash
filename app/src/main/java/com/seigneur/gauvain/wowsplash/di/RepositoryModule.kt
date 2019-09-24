package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    factory { PhotoRepository(get()) }
    factory { AuthRepository(get(), get()) }
    factory { CollectionsRepository(get()) }
    factory { UserRepository(get(), get()) }
    factory { SearchRepository(get()) }
    single { TokenRepository(get()) }
}