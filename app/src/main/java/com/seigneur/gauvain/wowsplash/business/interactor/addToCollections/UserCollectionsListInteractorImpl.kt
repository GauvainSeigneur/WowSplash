package com.seigneur.gauvain.wowsplash.business.interactor.addToCollections

import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.addToCollections.AddToCollectionsPresenter
import com.seigneur.gauvain.wowsplash.ui.addToCollections.UserCollectionsPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class UserCollectionsListInteractorImpl(
    private val userRepository: UserRepository,
    private val presenter: UserCollectionsPresenter
) : UserCollectionsListInteractor {

    private val compositeDisposable = CompositeDisposable()

    override fun getUser() {
        Timber.d("getUser called")
        fetchUser()
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

