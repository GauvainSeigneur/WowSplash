package com.seigneur.gauvain.wowsplash.di.photoList

import com.seigneur.gauvain.wowsplash.business.interactor.photoList.PhotoListInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photoList.PhotoListInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListPresenter
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val photoListModule = module {

    viewModel {
        PhotoListViewModel(get())
    } bind PhotoListPresenter::class

    factory<PhotoListInteractor> { (presenter: PhotoListViewModel) ->
        PhotoListInteractorImpl(presenter)
    }

}






