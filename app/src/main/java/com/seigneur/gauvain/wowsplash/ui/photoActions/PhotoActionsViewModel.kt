package com.seigneur.gauvain.wowsplash.ui.photoActions

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.photoActions.PhotoActionsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.TempDataRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoActionsViewModel : BaseViewModel(), KoinComponent,
    PhotoActionsPresenter {

    private val interactor by inject<PhotoActionsInteractor> { parametersOf(this) }

    //LiveData to be listen
    var onDisplayLoginRequestedMessage = MutableLiveData<Event<Int>>()
    var photoItemViewModel = MutableLiveData<PhotoItem>()
    var displayAddToCollectionsView = MutableLiveData<Event<PhotoItem>>()

    override fun presentGlobalError() {
    }

    override fun presentPhotoLiked(photoItem: PhotoItem) {
        photoItemViewModel.postValue(photoItem)
    }

    override fun presentPhotoRegistered(photoItem: PhotoItem) {
        photoItemViewModel.postValue(photoItem)
    }

    override fun presentUserCollectionsView(photoItem: PhotoItem) {
        displayAddToCollectionsView.postValue(Event(photoItem))
    }

    override fun presentLoginRequestedMessage() {
        onDisplayLoginRequestedMessage.postValue(Event(0))
    }

    fun likePhoto(photoItem: PhotoItem, isActionFromDetails: Boolean) {
        interactor.likePhoto(mDisposables, photoItem, isActionFromDetails)
    }

    fun onRegisterPhotoClicked(photoItem: PhotoItem) {
        interactor.openCollectionsOptions(photoItem)
    }

}
