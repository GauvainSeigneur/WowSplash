package com.seigneur.gauvain.wowsplash.di.logIn

import com.seigneur.gauvain.wowsplash.business.interactor.login.LogInInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.login.LogInInteractorImpl
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInPresenter
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val logInModule = module {

    viewModel {
        LogInViewModel(get())
    } bind LogInPresenter::class

    factory<LogInInteractor> { (logInPresenter: LogInViewModel) ->
        LogInInteractorImpl(get(), get(), logInPresenter)
    }

}






