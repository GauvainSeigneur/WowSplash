package com.seigneur.gauvain.wowsplash.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection.CollectionDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.home.PhotoViewModel
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_ALL
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import timber.log.Timber


class CollectionsViewModel(private val mCollectionsRepository: CollectionsRepository) :
    BaseViewModel(){

    var list: LiveData<PagedList<PhotoCollection>>? = null
    private var type: Int = COLLECTION_LIST_ALL
    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: CollectionDataSourceFactory by lazy {
        CollectionDataSourceFactory(
            mDisposables,
            mCollectionsRepository,
            type
        )
    }

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.collectionDataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.collectionDataSourceLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    fun init(inType: Int) {
        type = inType
        config.let {
            config = PagedList.Config.Builder()
                .setPageSize(15)
                .setInitialLoadSizeHint(15)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(photoDataSourceFactory, config!!).build()
        }
    }

    fun retry() {
        if (photoDataSourceFactory.collectionDataSourceLiveData.value != null)
            photoDataSourceFactory.collectionDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        if (photoDataSourceFactory.collectionDataSourceLiveData.value != null)
            photoDataSourceFactory.collectionDataSourceLiveData.value!!.invalidate()
    }


}
