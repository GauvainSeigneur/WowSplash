package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.data.provider.PhotoListDataProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PHOTO_LIST_DATA_PROVIDER_SCOPE_NAME ="PHOTO_LIST_DATA_PROVIDER_SCOPE_NAME"
const val PHOTO_LIST_DATA_PROVIDER_SESSION_ID="PHOTO_LIST_DATA_PROVIDER_SESSION_ID"

val photoListDataProviderModule = module {
    scope(named(PHOTO_LIST_DATA_PROVIDER_SCOPE_NAME)) {
        scoped { PhotoListDataProvider() }
    }
}