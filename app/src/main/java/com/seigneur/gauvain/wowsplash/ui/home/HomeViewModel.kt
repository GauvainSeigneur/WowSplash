package com.seigneur.gauvain.wowsplash.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class HomeViewModel(private val mPhotoRepository: PhotoRepository) :
    BaseViewModel() {

    var list: LiveData<PagedList<Photo>>? = null

    private val compositeDisposable = CompositeDisposable()

    private val photoDatasourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(compositeDisposable, mPhotoRepository, null)
    }

    private var config: PagedList.Config? = null

    val networkState: LiveData<NetworkState>
        get() =  Transformations.switchMap(photoDatasourceFactory.photoDataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDatasourceFactory.photoDataSourceLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }


    fun init() {
        if (config == null && list == null) {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(photoDatasourceFactory, config!!).build()
        }

    }

    fun retry() {
        if (photoDatasourceFactory.photoDataSourceLiveData.value != null)
            photoDatasourceFactory.photoDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        if (photoDatasourceFactory.photoDataSourceLiveData.value != null)
            photoDatasourceFactory.photoDataSourceLiveData.value!!.invalidate()
    }

    companion object {
        private val pageSize = 15
    }

}
