package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.provider.PhotoDetailsDataProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PHOTO_DETAILS_TEMP_SCOPE_NAME ="PHOTO_DETAILS_TEMP"
const val PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID="PHOTO_DETAILS_TEMP_SESSION"

val photoDetailsDataProviderModule = module {
    scope(named(PHOTO_DETAILS_TEMP_SCOPE_NAME)) {
        scoped { PhotoDetailsDataProvider() }
    }
}