package com.seigneur.gauvain.wowsplash.ui.photoDetails

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.photoDetails.PhotoDetailsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.provider.PhotoDetailsDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoDetailsViewModel : BaseViewModel(), PhotoDetailsPresenter, KoinComponent {

    // access to PhotoDetailsDataProvider session
    private val photoDetailsDataProviderSession = getKoin().getScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID)
    private val photoDetailsDataProvider = photoDetailsDataProviderSession.get<PhotoDetailsDataProvider>()

    private val interactor by inject<PhotoDetailsInteractor> { parametersOf(this) }


    override fun presentPhotoLiked(likeIt: Boolean) {

    }

    override fun presentGlobalError() {

    }

    override fun presentLoginRequestedMessage() {

    }

    override fun updateDataPhotoLiked(photo: Photo) {

    }

    fun getPhotoClicked(): MutableLiveData<PhotoItem> {
        return photoDetailsDataProvider.photoClicked
    }

    override fun onCleared() {
        photoDetailsDataProviderSession.close() // close the session when the viewModel is cleared
        super.onCleared()
    }

}
