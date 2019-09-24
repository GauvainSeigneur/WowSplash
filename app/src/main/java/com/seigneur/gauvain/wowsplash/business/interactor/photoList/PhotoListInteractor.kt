package com.seigneur.gauvain.wowsplash.business.interactor.photoList

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo

interface PhotoListInteractor {
    fun onPhotoClicked(photo: Photo?, pos: Int)
}

