package com.seigneur.gauvain.wowsplash.di.user

import com.seigneur.gauvain.wowsplash.business.interactor.user.UserInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.user.UserInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.user.UserPresenter
import com.seigneur.gauvain.wowsplash.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {

    viewModel {
        UserViewModel(get())
    } bind UserPresenter::class

    factory<UserInteractor> { (userPresenter: UserViewModel) ->
        UserInteractorImpl(get(), userPresenter)
    }

}






