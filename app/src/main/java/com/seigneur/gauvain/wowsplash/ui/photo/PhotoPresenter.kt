package com.seigneur.gauvain.wowsplash.ui.photo

interface PhotoPresenter {
    fun presentPhotoLiked(position: Int)
    fun presentPhotoDetails(position: Int)
    fun presentGlobalError()
}