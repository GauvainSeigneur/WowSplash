package com.seigneur.gauvain.wowsplash.ui.photoDetails

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo

interface PhotoDetailsPresenter {
    fun presentPhotoLiked(likeIt:Boolean)
    fun updateDataPhotoLiked(photo:Photo)
    fun presentGlobalError()
    fun presentLoginRequestedMessage()
}