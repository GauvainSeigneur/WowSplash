package com.seigneur.gauvain.wowsplash.business.interactor

import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class UserInteractor(
    private val userRepository: UserRepository,
    private val compositeDisposable: CompositeDisposable,
    private val userCallback: UserCallback
) {

    fun getMe() {
        val accessToken = AuthRepository.accessToken
        if (accessToken.isNullOrEmpty()) {
            userCallback.onError(Throwable("NO TOKEN AVAILABLE", null))
        } else {
            fecthMeFromAPI()
        }
    }

    private fun fecthMeFromAPI() {
        compositeDisposable.add(userRepository.getMe()
            .subscribeBy(
                onSuccess = {
                    userCallback.onMeFetchedFromAPI(it)
                },
                onError = {
                    userCallback.onError(it)
                }
            )
        )
    }

    interface UserCallback {
        fun onMeFetchedFromAPI(me: User)
        fun onError(throwable: Throwable)
    }
}

