package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AddToCollectionsInteractorImpl(
    private val collectionsRepository: CollectionsRepository,
    private val presenter: AddToCollectionsPresenter
) : AddToCollectionsInteractor, KoinComponent {

    private val compositeDisposable = CompositeDisposable()
    var currentPhotoCollection=ArrayList<PhotoCollection>()

    override fun createCollection() {

    }

    override fun addPhotoToCollection(
        collectionId: String,
        photoId: String,
        position: Int
    ) {
        compositeDisposable.add(collectionsRepository.registerPhotoInCollection(
            collectionId,
            photoId
        )
            .doOnSubscribe {
                presenter.presentPhotoAddedToCollection(position, true)
                presenter.presentRequesLoader(true)
            }
            .subscribeBy(
                onError = {
                    presenter.presentRequesLoader(false)
                },
                onSuccess = {
                    Timber.d("add photo success ${it.collection} $collectionId")
                    presenter.presentRequesLoader(false)
                    presenter.presentCollectionUpdated(it.collection!!, true)
                }
            )
        )
    }

    override fun removePhotoFromCollection(
        collectionId: String, photoId: String, position: Int
    ) {
        compositeDisposable.add(collectionsRepository.removePhotoFromCollection(
            collectionId,
            photoId
        )
            .doOnSubscribe {
                presenter.presentPhotoAddedToCollection(position, false)
                presenter.presentRequesLoader(true)
            }
            .subscribeBy(
                onError = {
                    presenter.presentRequesLoader(false)
                },
                onSuccess = {
                    presenter.presentRequesLoader(false)
                    presenter.presentCollectionUpdated(it.collection!!, false)
                }
            )
        )
    }


    override fun clear() {
        compositeDisposable.clear()
    }

}

