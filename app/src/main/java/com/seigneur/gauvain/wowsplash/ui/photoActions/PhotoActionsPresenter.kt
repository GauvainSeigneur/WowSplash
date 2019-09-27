package com.seigneur.gauvain.wowsplash.ui.photoActions

import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem

interface PhotoActionsPresenter {
    fun presentPhotoLiked(photoItem: PhotoItem)
    fun presentPhotoRegistered(photoItem: PhotoItem)
    fun presentUserCollectionsView(photoItem: PhotoItem)
    fun presentGlobalError()
    fun presentLoginRequestedMessage()
}