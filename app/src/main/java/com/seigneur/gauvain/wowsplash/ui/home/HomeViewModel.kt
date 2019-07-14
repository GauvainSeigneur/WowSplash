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
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class HomeViewModel(private val mPhotoRepository: PhotoRepository) : BaseViewModel() {

    var mPhotoResult = MutableLiveData<PhotoResult>()

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

    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(mDisposables, mPhotoRepository)
    }


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


    /**
     * Get photos
     */
    fun getPhotos() {
        mDisposables.add(mPhotoRepository.getPhotos()
            .subscribeBy(
                onNext = {
                    Timber.d("onNext $it")
                    mPhotoResult.value = PhotoResult.PhotoList(it)
                    //mListResult.value = ListResult(inList = it)
                },
                onError = {
                    Timber.d("onError $it")
                    mPhotoResult.value = PhotoResult.PhotoError(it)
                    //mListResult.value = ListResult(inError = it)
                },
                onComplete = { Timber.d("onComplete getPhotos()")}
            )
        )
    }

    sealed class PhotoResult {
        data class PhotoList(val inList: List<Photo>? = null) : PhotoResult()
        data class PhotoError(val inError: Throwable? = null) : PhotoResult()
    }

    companion object {
        private val pageSize = 15
    }

}