package com.seigneur.gauvain.wowsplash.ui.addToCollections

interface AddToCollectionsPresenter {
    fun presentPhotoAddedToCollection(position: Int, selected: Boolean)
    fun presentRequesLoader(visible:Boolean)
}