package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections

import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AddToCollectionsInteractorImpl(
    private val collectionsRepository:CollectionsRepository,
    private val presenter: AddToCollectionsPresenter
) : AddToCollectionsInteractor {

    private val compositeDisposable = CompositeDisposable()

    override fun createCollection() {

    }

    override fun addPhotoToCollection(collectionId: String, photoId: String, position:Int) {
        compositeDisposable.add(collectionsRepository.registerPhotoInCollection(collectionId, photoId)
            .doOnSubscribe {
                presenter.presentPhotoAddedToCollection(position, true)
                presenter.presentRequesLoader(true)
            }
            .subscribeBy(
                onError = {presenter.presentRequesLoader(false)},
                onSuccess = {
                    Timber.d("add collection success $it")
                    presenter.presentRequesLoader(false)
                }
            )
        )
    }

    override fun removePhotoFromCollection(collectionId: String, photoId: String, position: Int) {

    }

    override fun clear() {
        compositeDisposable.clear()
    }

}

