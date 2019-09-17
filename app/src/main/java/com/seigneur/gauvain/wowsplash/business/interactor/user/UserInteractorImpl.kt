package com.seigneur.gauvain.wowsplash.business.interactor.user

import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.user.UserPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class UserInteractorImpl(
    private val userRepository: UserRepository,
    private val userPresenter: UserPresenter
):UserInteractor {

    val compositeDisposable= CompositeDisposable()

    override fun getMe() {
        val accessToken = AuthRepository.accessToken
        if (accessToken.isNullOrEmpty()) {
            userPresenter.onError(Throwable("NO TOKEN AVAILABLE", null))
        } else {
            fecthMeFromAPI()
        }
    }

    override fun close() {
        compositeDisposable.clear()
    }

    private fun fecthMeFromAPI() {
        compositeDisposable.add(userRepository.getMe()
            .subscribeBy(
                onSuccess = {
                    userPresenter.onMeFetchedFromAPI(it)
                },
                onError = {
                    userPresenter.onError(it)
                }
            )
        )
    }
}

