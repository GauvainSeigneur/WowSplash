package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections

import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AddToCollectionsInteractorImpl(
    private val userRepository: UserRepository,
    private val presenter: AddToCollectionsPresenter
) : AddToCollectionsInteractor {

    private val compositeDisposable = CompositeDisposable()

    override fun getUser() {
        fetchUser()
    }

    override fun createCollection() {

    }

    override fun addPhotoToCollection() {

    }

    override fun clear() {
        compositeDisposable.clear()
    }

    private fun fetchUser() {
        compositeDisposable.add(userRepository.getMeFromDataBase()
            .subscribeBy(
                onSuccess = {
                    presenter.presentUserCollections(it.username)
                },
                onError = { fetchUserFromApi() },
                onComplete = { fetchUserFromApi() }
            ))
    }

    private fun fetchUserFromApi() {
        compositeDisposable.add(userRepository.getMe()
            .flatMap {
                presenter.presentUserCollections(it.username)
                return@flatMap userRepository.insertUserInDataBase(it)
            }
            .subscribeBy(
                onSuccess = {
                    //do something ?
                },
                onError = {
                    //todo
                }
            ))
    }
}

