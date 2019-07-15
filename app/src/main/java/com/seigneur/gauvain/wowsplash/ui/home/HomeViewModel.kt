package com.seigneur.gauvain.wowsplash.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.base.list.NetworkState
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoDataSourceFactory
import timber.log.Timber

class HomeViewModel(private val mPhotoRepository: PhotoRepository) : BaseViewModel() {

    var shotList: LiveData<PagedList<Photo>>? = null
    private var config: PagedList.Config? = null

    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(mDisposables, mPhotoRepository)
    }

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

}
