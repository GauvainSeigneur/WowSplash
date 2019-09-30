package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractor
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class AddToCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseViewModel(),
    AddToCollectionsPresenter,
    KoinComponent {

    val userName = MutableLiveData<Event<String>>()

    private val interactor by inject<AddToCollectionsInteractor> { parametersOf(this) }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    override fun presentUserCollections(name: String) {
        Timber.d("presentUserCollections called $name")
        userName.postValue(Event(name))
    }

    fun fetchUserName(){
        interactor.getUser()
    }

}
