package com.seigneur.gauvain.wowsplash.di.userCollectionsListViewModel

import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.UserCollectionsListInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.UserCollectionsListInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.addToCollections.UserCollectionsListViewModel
import com.seigneur.gauvain.wowsplash.ui.addToCollections.UserCollectionsPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val userCollectionsModule = module {

    viewModel {
        UserCollectionsListViewModel(get())
    }bind UserCollectionsPresenter::class

    factory<UserCollectionsListInteractor> { (presenter: UserCollectionsPresenter) ->
        UserCollectionsListInteractorImpl( get(), presenter)
    }

}






