package com.seigneur.gauvain.wowsplash.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.BasePagedListViewModel
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoDataSourceFactory
import timber.log.Timber

class HomeViewModel(private val mPhotoRepository: PhotoRepository) :
    BasePagedListViewModel<Long, Photo>(15) {

    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(mDisposables, mPhotoRepository)
    }

    override val dataSourceFactory: DataSource.Factory<Long, Photo>
        get() = photoDataSourceFactory

    override var list: LiveData<PagedList<Photo>>? = null
        get() = field

    override val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(photoDataSourceFactory.factoryListLiveData)
        { it.networkState }

    override val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.factoryListLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    override fun retry() {
        photoDataSourceFactory.factoryListLiveData.value?.retry()
    }

    override fun refresh() {
        photoDataSourceFactory.factoryListLiveData.value?.invalidate()
    }
/*
    var shotList: LiveData<PagedList<Photo>>? = null
    private var config: PagedList.Config? = null



    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.photoLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(photoDataSourceFactory.photoLiveData)
        { it.networkState }

    fun init() {
        if (config == null && shotList == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            shotList = LivePagedListBuilder(photoDataSourceFactory, config!!).build()
        }
    }

    fun retry() {
        photoDataSourceFactory.photoLiveData.value?.retry()
    }


    fun refresh() {
        photoDataSourceFactory.photoLiveData.value?.invalidate()
    }


    companion object {
        private val pageSize = 15
    }
    */

}
