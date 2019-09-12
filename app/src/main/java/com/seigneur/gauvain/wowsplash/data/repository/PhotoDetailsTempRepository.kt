package com.seigneur.gauvain.wowsplash.data.repository

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.utils.event.Event

class PhotoDetailsTempRepository()  {

    val photoClicked = MutableLiveData<Event<Photo>>()
}
