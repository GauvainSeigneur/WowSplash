package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.photoList.PhotoPresenter


/**
 *  Must be used only by ViewModel which inject all dependencies
 */
interface PhotoInteractor {
    fun setUpPresenter(photoPresenter: PhotoPresenter)
    fun likePhoto(id: String?, pos: Int)
    fun closeObservable()
    fun onPhotoClicked(photo:Photo?)
}

