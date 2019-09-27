package com.seigneur.gauvain.wowsplash.di.photo

import com.seigneur.gauvain.wowsplash.business.interactor.photoActions.PhotoActionsInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.photoActions.PhotoActionsInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.photoActions.PhotoActionsViewModel
import com.seigneur.gauvain.wowsplash.ui.photoActions.PhotoActionsPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val photoModule = module {

    viewModel {
        PhotoActionsViewModel()
    } bind PhotoActionsPresenter::class

    factory<PhotoActionsInteractor> { (photoActionsPresenter: PhotoActionsViewModel) ->
        PhotoActionsInteractorImpl(get(), get(), photoActionsPresenter)
    }

}






