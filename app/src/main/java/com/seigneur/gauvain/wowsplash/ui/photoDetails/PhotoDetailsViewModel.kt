package com.seigneur.gauvain.wowsplash.ui.photoDetails

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event

class PhotoDetailsViewModel(private val mPhotoRepository: PhotoRepository) :
    BaseViewModel() {

    fun getPhotoClicked():MutableLiveData<Event<Photo>> {
        return mPhotoRepository.photoClicked
    }

}
