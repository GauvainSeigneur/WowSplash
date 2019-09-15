package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractorImpl
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val interactorModule = module {

    // Will match type Service only
    factory { PhotoInteractorImpl(
        CompositeDisposable(),
        get(),
        get()
    ) as PhotoInteractor
    }

}






