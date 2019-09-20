package com.seigneur.gauvain.wowsplash.di.photo

import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoPresenter
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val photoModule = module {

    viewModel {
        PhotoViewModel(get(), get())
    } bind PhotoPresenter::class

    factory<PhotoInteractor> { (photoPresenter: PhotoViewModel) ->
        PhotoInteractorImpl(get(), photoPresenter)
    }

}






