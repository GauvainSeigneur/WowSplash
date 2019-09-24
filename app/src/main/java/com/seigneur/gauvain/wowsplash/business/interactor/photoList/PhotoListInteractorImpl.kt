package com.seigneur.gauvain.wowsplash.business.interactor.photoList

import com.seigneur.gauvain.wowsplash.data.provider.PhotoDetailsDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListPresenter
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

class PhotoListInteractorImpl(private val presenter: PhotoListPresenter) : PhotoListInteractor, KoinComponent {

    override fun onPhotoClicked(photo: Photo?, pos: Int) {
        photo?.let {
            //Create a session for tempProvider
            val temporaryDataProviderSession =
                getKoin().getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            //Create an instance from that session
            val temporaryDataProvider = temporaryDataProviderSession.get<PhotoDetailsDataProvider>()
            //store data
            temporaryDataProvider.photoClicked.postValue(PhotoItem(it, pos))
        }
        presenter.presentPhotoDetails(pos)
    }
}

