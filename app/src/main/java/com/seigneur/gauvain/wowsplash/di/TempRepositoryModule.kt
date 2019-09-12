package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.repository.PhotoDetailsTempRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tempRepositoryModule = module {
    scope(named("PHOTO_DETAILS_TEMP")) {
        scoped { PhotoDetailsTempRepository() }
    }
}