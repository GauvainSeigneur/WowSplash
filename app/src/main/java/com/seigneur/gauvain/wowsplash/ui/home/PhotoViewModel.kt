package com.seigneur.gauvain.wowsplash.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PhotoViewModel(private val mPhotoRepository: PhotoRepository) :
    BaseViewModel() {

    var list: LiveData<PagedList<Photo>>? = null
    private var orderBy: String? = null

    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(
            mDisposables,
            mPhotoRepository,
            PHOTO_LIST_HOME,
            orderBy,
            null
        )
    }

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.photoDataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.photoDataSourceLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    fun init(inOrderBy: String?) {
        orderBy = inOrderBy
        config.let {
            config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(photoDataSourceFactory, config!!).build()
        }
    }

    fun retry() {
        if (photoDataSourceFactory.photoDataSourceLiveData.value != null)
            photoDataSourceFactory.photoDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        if (photoDataSourceFactory.photoDataSourceLiveData.value != null)
            photoDataSourceFactory.photoDataSourceLiveData.value!!.invalidate()
    }


    fun likePhoto(id:String?){
        id?.let {
            mDisposables.add(
                mPhotoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = { Timber.d("photo liked") },
                        onError =  { Timber.d("error on photo liked $it") }
                    )
            )
        }
    }

    companion object {
        private val pageSize = 15
    }

}
