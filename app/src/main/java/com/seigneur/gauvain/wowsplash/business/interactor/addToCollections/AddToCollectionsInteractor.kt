package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections

interface AddToCollectionsInteractor {
    fun addPhotoToCollection(collectionId: String, photoId: String,position:Int)
    fun removePhotoFromCollection(collectionId: String, photoId: String,position:Int)
    fun createCollection()
    fun clear()
}

