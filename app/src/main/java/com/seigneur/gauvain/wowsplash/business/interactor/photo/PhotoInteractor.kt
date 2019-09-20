package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import io.reactivex.disposables.CompositeDisposable

interface PhotoInteractor {
    fun likePhoto(disposable: CompositeDisposable,id: String?, pos: Int)
    fun onPhotoClicked(photo: Photo?, pos:Int)

}

