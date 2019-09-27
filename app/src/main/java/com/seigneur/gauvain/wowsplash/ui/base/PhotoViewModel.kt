package com.seigneur.gauvain.wowsplash.ui.base

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoViewModel : BaseViewModel(), KoinComponent, PhotoPresenter {

    private val interactor by inject<PhotoInteractor> { parametersOf(this) }

    //LiveData to be listen
    var onDisplayLoginRequestedMessage = MutableLiveData<Event<Int>>()
    //
    var photoItem: PhotoItem? = null
    var photoItemViewModel = MutableLiveData<PhotoItem>()

    override fun presentGlobalError() {

    }

    override fun presentPhotoLiked(position: Int, liked: Boolean) {
        photoItem?.photo?.liked_by_user = liked
        photoItemViewModel.postValue(photoItem)
    }

    override fun updateDataPhotoLiked(position: Int, photo: Photo) {
        //onPhotoDataUpdated.postValue(Event(Pair(position, photo)))
    }

    override fun presentLoginRequestedMessage() {
        onDisplayLoginRequestedMessage.postValue(Event(0))
    }

    fun likePhoto(liked: Boolean) {
        interactor.likePhoto(mDisposables, photoItem, liked)
    }

}
