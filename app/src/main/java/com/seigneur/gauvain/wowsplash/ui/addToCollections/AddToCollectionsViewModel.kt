package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class AddToCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseViewModel(),
    AddToCollectionsPresenter,
    KoinComponent {

    val userName = MutableLiveData<Event<String>>()
    private val selectedList = ArrayList<SelectedCollection>()
    val selectedCollections = MutableLiveData<List<SelectedCollection>>()

    private val interactor by inject<AddToCollectionsInteractor> { parametersOf(this) }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    override fun presentUserCollections(name: String) {
        Timber.d("presentUserCollections called $name")
        userName.postValue(Event(name))
    }

    fun fetchUserName() {
        interactor.getUser()
    }

    fun onAddClicked(position: Int, item: CollectionItem?) {
        Timber.d("lol it is called ")
        item?.let {
            if (selectedList.isEmpty()) {
                item.selected = true
                selectedList.add(SelectedCollection(position, item))
            } else {
                for (selectedItem in selectedList) {
                    if (selectedItem.collectionItem.photoCollection.id == item.photoCollection.id) {
                        val value = !selectedItem.collectionItem.selected
                        selectedItem.collectionItem.selected = value //update its value
                    } else {
                        item.selected = true
                        selectedList.add(SelectedCollection(position, item))
                    }
                }
            }

            selectedCollections.postValue(selectedList.toList())

            Timber.d("lol it is posted ${selectedCollections.value}")
        }
    }


    data class SelectedCollection(val position: Int, val collectionItem: CollectionItem)

}
