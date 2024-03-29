package com.seigneur.gauvain.wowsplash.di

import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsListViewModel
import com.seigneur.gauvain.wowsplash.ui.splash.SplashViewModel
import com.seigneur.gauvain.wowsplash.ui.collections.CollectionsViewModel
import com.seigneur.gauvain.wowsplash.ui.postPhoto.PostPhotoViewModel
import com.seigneur.gauvain.wowsplash.ui.search.photo.SearchPhotoViewModel
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import com.seigneur.gauvain.wowsplash.ui.search.collection.SearchCollectionViewModel
import com.seigneur.gauvain.wowsplash.ui.search.user.SearchUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CollectionsViewModel(get())
    }

    viewModel {
        SearchViewModel()
    }

    viewModel {
        SearchPhotoViewModel(get())
    }

    viewModel {
        SearchCollectionViewModel(get())
    }

    viewModel {
        AddToCollectionsListViewModel(get(), get())
    }

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        PostPhotoViewModel()
    }

    viewModel {
        SearchUserViewModel(get())
    }

}






