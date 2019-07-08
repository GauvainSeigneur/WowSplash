package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val viewModelModule =module {

       viewModel {
           HomeViewModel(get())
       }

   }






