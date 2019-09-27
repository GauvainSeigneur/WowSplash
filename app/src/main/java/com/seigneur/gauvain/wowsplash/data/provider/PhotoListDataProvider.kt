package com.seigneur.gauvain.wowsplash.data.provider

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem

/**
 * Class to be created and immediately used
 * its session must be closed when the view doesn't use it anymore
 */
class PhotoListDataProvider  {
    var photoModified = MutableLiveData<PhotoItem>()
}
