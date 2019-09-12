package com.seigneur.gauvain.wowsplash.ui.photoDetails

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoDetailsTempRepository
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.android.ext.android.getKoin
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

class PhotoDetailsViewModel(private val mPhotoRepository: PhotoRepository) :
    BaseViewModel(), KoinComponent {

    // create the scope
    val tempRepoSession = getKoin().getScope("myScope1")
    val photoDetailsTempRepository = tempRepoSession.get<PhotoDetailsTempRepository>()

    fun getPhotoClicked(): MutableLiveData<Event<Photo>> {
        return photoDetailsTempRepository.photoClicked
    }

    override fun onCleared() {
        super.onCleared()
        tempRepoSession.close() // close it
    }

}
