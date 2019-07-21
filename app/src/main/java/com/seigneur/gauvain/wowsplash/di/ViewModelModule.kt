package com.seigneur.gauvain.wowsplash.di
import com.seigneur.gauvain.wowsplash.ui.collections.CollectionsViewModel
import com.seigneur.gauvain.wowsplash.ui.home.HomeViewModel
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInViewModel
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val viewModelModule =module {

        viewModel {
            HomeViewModel(get())
        }

        viewModel {
            HomeViewModel(get())
        }


        viewModel {
            CollectionsViewModel(get())
        }

        viewModel {
           SearchViewModel(get())
       }

        viewModel {
            LogInViewModel(get())
        }

   }






