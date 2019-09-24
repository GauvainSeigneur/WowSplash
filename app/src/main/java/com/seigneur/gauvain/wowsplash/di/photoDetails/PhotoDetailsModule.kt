package com.seigneur.gauvain.wowsplash.di.photoDetails

import com.seigneur.gauvain.wowsplash.business.interactor.photoDetails.PhotoDetailsInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photoDetails.PhotoDetailsInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.photoDetails.PhotoDetailsPresenter
import com.seigneur.gauvain.wowsplash.ui.photoDetails.PhotoDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val photoDetailsModule = module {

    viewModel {
        PhotoDetailsViewModel()
    } bind PhotoDetailsPresenter::class

    factory<PhotoDetailsInteractor> { (presenter: PhotoDetailsViewModel) ->
        PhotoDetailsInteractorImpl(get(), presenter)
    }

}






