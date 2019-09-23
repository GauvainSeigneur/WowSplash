package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import io.reactivex.disposables.CompositeDisposable

interface PhotoInteractor {
    fun init()
    fun likePhoto(disposable: CompositeDisposable, photoItem: PhotoItem?, isLiked: Boolean)
    fun onPhotoClicked(photo: Photo?, pos: Int)
}

