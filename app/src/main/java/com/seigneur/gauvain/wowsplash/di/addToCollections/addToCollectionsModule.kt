package com.seigneur.gauvain.wowsplash.di.addToCollections

import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsListViewModel
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val addToCollectionsModule = module {

    viewModel {
        AddToCollectionsListViewModel(get())
    }

    viewModel {
        AddToCollectionsViewModel(get())
    } bind AddToCollectionsPresenter::class

    factory<AddToCollectionsInteractor> { (presenter: AddToCollectionsPresenter) ->
        AddToCollectionsInteractorImpl(get(), presenter)
    }

}






