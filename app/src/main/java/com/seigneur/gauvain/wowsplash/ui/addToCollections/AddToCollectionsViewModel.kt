package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class AddToCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseViewModel(),
    AddToCollectionsPresenter,
    KoinComponent {

    val userName = MutableLiveData<Event<String>>()
    var photoId: String? = null
    var selectedCollectionEvent = MutableLiveData<Event<SelectedPosition>>()
    var requestLoaderVisibility = MutableLiveData<Boolean>()

    private val interactor by inject<AddToCollectionsInteractor> { parametersOf(this) }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    override fun presentRequesLoader(visibility: Boolean) {
        requestLoaderVisibility.postValue(visibility)
    }

    override fun presentPhotoAddedToCollection(position: Int, selected: Boolean) {
        selectedCollectionEvent.postValue(Event(SelectedPosition(position, selected)))
    }


    fun onAddClicked(position: Int, item: CollectionItem?) {
        item?.let {
            photoId?.let {
                if (!item.selected) {
                    interactor.addPhotoToCollection(item.photoCollection.id, it, position)
                } else {

                    interactor.removePhotoFromCollection(item.photoCollection.id, it, position)
                }
            }

        }
    }

    class SelectedPosition(val position: Int, val selected: Boolean)

}
