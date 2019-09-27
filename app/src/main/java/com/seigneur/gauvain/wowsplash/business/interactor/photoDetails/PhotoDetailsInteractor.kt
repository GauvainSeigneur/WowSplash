package com.seigneur.gauvain.wowsplash.business.interactor.photoDetails

import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem

interface PhotoDetailsInteractor {
    fun onPhotoModified(photoItem: PhotoItem)
}

