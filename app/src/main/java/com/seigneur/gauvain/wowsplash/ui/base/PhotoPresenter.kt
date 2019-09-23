package com.seigneur.gauvain.wowsplash.ui.base

import com.seigneur.gauvain.wowsplash.data.model.photo.Photo

interface PhotoPresenter {
    fun presentPhotoLiked(position: Int, likeIt:Boolean)
    fun updateDataPhotoLiked(position: Int, photo:Photo)
    fun presentPhotoDetails(position: Int)
    fun presentGlobalError()
    fun presentLoginRequestedMessage()
}