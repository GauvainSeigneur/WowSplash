package com.seigneur.gauvain.wowsplash.ui.photoDetails

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import org.koin.core.KoinComponent

class PhotoDetailsViewModel(private val mPhotoRepository: PhotoRepository) :
    BaseViewModel(), KoinComponent {

    // create the scope
    private val temporaryDataSession = getKoin().getScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID)
    private val temporaryDataProvider = temporaryDataSession.get<TemporaryDataProvider>()

    fun getPhotoClicked(): MutableLiveData<Photo> {
        return temporaryDataProvider.photoClicked
    }

    override fun onCleared() {
        super.onCleared()
        temporaryDataSession.close() // close it
    }

}
