package com.seigneur.gauvain.wowsplash.ui.addToCollections

import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection

interface AddToCollectionsPresenter {
    fun presentPhotoAddedToCollection(position: Int, selected: Boolean)
    fun presentRequesLoader(visible:Boolean)
    fun presentCollectionUpdated(collection: PhotoCollection, added:Boolean)
}