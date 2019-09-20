package com.seigneur.gauvain.wowsplash.ui.photo

interface PhotoPresenter {
    fun presentPhotoLiked(position: Int, likeIt:Boolean)
    fun presentPhotoDetails(position: Int)
    fun presentGlobalError()
}