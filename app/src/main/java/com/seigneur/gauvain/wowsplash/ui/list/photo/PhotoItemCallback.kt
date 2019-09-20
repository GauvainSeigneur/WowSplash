package com.seigneur.gauvain.wowsplash.ui.list.photo

interface PhotoItemCallback {
    fun onPhotoClicked(position: Int)
    fun onPhotoLiked(position: Int)
}
