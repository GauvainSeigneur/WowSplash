package com.seigneur.gauvain.wowsplash.ui.base

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.photo.PhotoInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoPresenter
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoViewModel : BaseViewModel(), KoinComponent, PhotoPresenter {

    private val interactor by inject<PhotoInteractor> { parametersOf(this) }

    //LiveData to be listen
    var goToDetailsEvent = MutableLiveData<Event<Int>>()
    var onPhotoLikedEvent = MutableLiveData<Event<Pair<Int, Boolean>>>()
    var onPhotoDataUpdated = MutableLiveData<Event<Pair<Int, Photo>>>()
    var onDisplayLoginRequestedMessage = MutableLiveData<Event<Int>>()

    override fun presentGlobalError() {

    }

    override fun presentPhotoDetails(position: Int) {
        goToDetailsEvent.postValue(Event(position))
    }

    override fun presentPhotoLiked(position: Int, liked: Boolean) {
        onPhotoLikedEvent.postValue(Event(Pair(position, liked)))
    }

    override fun updateDataPhotoLiked(position: Int, photo: Photo) {
        onPhotoDataUpdated.postValue(Event(Pair(position, photo)))
    }

    override fun presentLoginRequestedMessage() {
        onDisplayLoginRequestedMessage.postValue(Event(0))
    }

    fun likePhoto(id: String?, pos: Int, liked: Boolean) {
        interactor.likePhoto(mDisposables, id, pos, liked)
    }

    fun setPhotoClicked(photo: Photo?, position: Int) {
        interactor.onPhotoClicked(photo, position)
    }
}
