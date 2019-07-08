package com.seigneur.gauvain.wowsplash.ui

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class HomeViewModel(private val mPhotoRepository: PhotoRepository) : BaseViewModel() {

    private var mListResult = MutableLiveData<ListResult>()
    var mPhotoResult = MutableLiveData<PhotoResult>()

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


    /**
     * TODO: use sealed class instead
     * class dedicated to manage UI related data
     */
    data class ListResult(val inList: List<Photo>? = null, val inError: Throwable? = null)

    sealed class PhotoResult {
        data class PhotoList(val inList: List<Photo>? = null) : PhotoResult()
        data class PhotoError(val inError: Throwable? = null) : PhotoResult()
    }

}
