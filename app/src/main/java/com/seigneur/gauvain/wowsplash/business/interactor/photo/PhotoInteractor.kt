package com.seigneur.gauvain.wowsplash.business.interactor.photo


/**
 *  Must be used only by ViewModel which inject all dependencies
 */
interface PhotoInteractor {
    fun likePhoto(id: String?, pos: Int)
}

