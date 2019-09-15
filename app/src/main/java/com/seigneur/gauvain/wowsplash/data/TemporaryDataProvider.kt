package com.seigneur.gauvain.wowsplash.data

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo

/**
 * Class to be created and use immediately
 * its session must be closed when the view doesn't use it anymore
 */
class TemporaryDataProvider  {
    var photoClicked = MutableLiveData<Photo>()
}
