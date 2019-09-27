package com.seigneur.gauvain.wowsplash.business.interactor.photoActions

import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import io.reactivex.disposables.CompositeDisposable

interface PhotoActionsInteractor {
    fun likePhoto(disposable: CompositeDisposable, photoItem: PhotoItem, isActionFromDetails: Boolean)
    fun openCollectionsOptions(photoItem: PhotoItem)
    fun onPhotoAddedToCollecion(disposable: CompositeDisposable, photoItem: PhotoItem, isActionFromDetails: Boolean)
}

