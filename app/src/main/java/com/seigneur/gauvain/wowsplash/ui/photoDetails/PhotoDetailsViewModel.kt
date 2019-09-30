package com.seigneur.gauvain.wowsplash.ui.photoDetails

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.photoDetails.PhotoDetailsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.TempDataRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class PhotoDetailsViewModel : BaseViewModel(), PhotoDetailsPresenter, KoinComponent {

    private val interactor by inject<PhotoDetailsInteractor> { parametersOf(this) }

    var photoItem = MutableLiveData<PhotoItem>()

}
