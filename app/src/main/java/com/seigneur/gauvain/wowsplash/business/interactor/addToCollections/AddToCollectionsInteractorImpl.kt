package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections

import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AddToCollectionsInteractorImpl(
    private val userRepository: UserRepository,
    private val presenter: AddToCollectionsPresenter
) :
    AddToCollectionsInteractor {

    override fun getUserCollections() {

    }

    override fun createCollection() {

    }

    override fun addPhotoToCollection() {

    }
}

