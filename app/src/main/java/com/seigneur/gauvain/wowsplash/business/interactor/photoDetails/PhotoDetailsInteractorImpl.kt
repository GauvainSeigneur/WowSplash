package com.seigneur.gauvain.wowsplash.business.interactor.photoDetails


import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.photoDetails.PhotoDetailsPresenter
import org.koin.core.KoinComponent

class PhotoDetailsInteractorImpl(
    private val photoRepository: PhotoRepository,
    private val presenter: PhotoDetailsPresenter
) : PhotoDetailsInteractor, KoinComponent {
    override fun onPhotoModified(photoItem: PhotoItem) {

    }
}

