package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single { PhotoRepository(get()) }
    single { AuthRepository(get(), get()) }
    single { CollectionsRepository(get()) }
    single { UserRepository(get(), get()) }
    single { SearchRepository(get()) }
}