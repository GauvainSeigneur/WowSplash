package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PhotoRepository(get()) }
    single { AuthRepository(get()) }
    single { CollectionsRepository(get()) }
}