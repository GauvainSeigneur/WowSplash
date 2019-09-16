package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import org.koin.dsl.module

val interactorModule = module {

    factory {
        PhotoInteractor(get(), get())
    }

}






