package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.repository.PhotoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PhotoRepository(get()) }
}