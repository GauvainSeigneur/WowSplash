package com.seigneur.gauvain.wowsplash.di.photo

import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListPresenter
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val photoModule = module {

    viewModel {
        PhotoListViewModel(get())
    } bind PhotoListPresenter::class

    factory<PhotoInteractor> { (photoPresenter: PhotoListViewModel) ->
        PhotoInteractorImpl(get(), get(), photoPresenter)
    }

}






