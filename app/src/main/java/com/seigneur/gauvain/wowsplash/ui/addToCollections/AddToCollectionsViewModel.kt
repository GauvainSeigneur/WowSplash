package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.addToCollections.AddToCollectionsInteractor
import com.seigneur.gauvain.wowsplash.data.model.photo.CollectionItem
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.data.repository.TempDataRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import retrofit2.adapter.rxjava2.Result.response
import android.R.id
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import timber.log.Timber


class AddToCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseViewModel(),
    AddToCollectionsPresenter,
    KoinComponent {

    val userName = MutableLiveData<Event<String>>()
    var photoItem: PhotoItem? = null
    var currentUserCollection = ArrayList<PhotoCollection>()
    var selectedCollectionEvent = MutableLiveData<Event<SelectedPosition>>()
    var requestLoaderVisibility = MutableLiveData<Boolean>()

    private val interactor by inject<AddToCollectionsInteractor> { parametersOf(this) }
    private val tempDataRepository by inject<TempDataRepository>()

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

    override fun presentCollectionUpdated(collection: PhotoCollection, added: Boolean) {
        photoItem?.let {
            if (added) {
                currentUserCollection.add(collection)

            } else {
                val iterator = currentUserCollection.listIterator()
                while (iterator.hasNext()) {
                    if (iterator.next().id == collection.id) {
                        iterator.remove()
                    }
                }
                //notify photoItem if user has registered or removed the list
                tempDataRepository.photoItemModifiedFromAddCollection.postValue(
                    Event(
                        TempDataRepository.BookmarkedItem(
                            it.position,
                            currentUserCollection.isNotEmpty()
                        )
                    )
                )

            }

            Timber.d("currentIds after ${currentUserCollection.size}")

        }

    }


    fun onAddClicked(position: Int, item: CollectionItem?) {
        item?.let {
            photoItem?.let {
                if (!item.selected) {
                    interactor.addPhotoToCollection(
                        item.photoCollection.id,
                        it.photo.id,
                        position
                    )
                } else {
                    interactor.removePhotoFromCollection(
                        item.photoCollection.id, it.photo.id, position
                    )
                }
            }

        }
    }

    class SelectedPosition(val position: Int, val selected: Boolean)

}
