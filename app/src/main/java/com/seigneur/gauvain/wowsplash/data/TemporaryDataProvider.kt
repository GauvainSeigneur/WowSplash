package com.seigneur.gauvain.wowsplash.data

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem

/**
 * Class to be created and immediately used
 * its session must be closed when the view doesn't use it anymore
 */
class TemporaryDataProvider  {
    var photoClicked = MutableLiveData<PhotoItem>()
    var photoItemModified = MutableLiveData<PhotoItem>()
}
