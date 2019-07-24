package com.seigneur.gauvain.wowsplash.business.interactor

import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AccessTokenInteractor(private val authRepository: AuthRepository,
                            private val compositeDisposable: CompositeDisposable,
                            private val accessTokenInteractorCallback: AccessTokenInteractorCallback
) {

    fun fetchAccessTokenFromDB(){
        compositeDisposable.add(authRepository.getAccessTokenFromDB()
            .subscribeBy(
                onSuccess = {
                    AuthRepository.accessToken = it.access_token
                    accessTokenInteractorCallback.onLocalAccessTokenFetched(it.access_token)
                },
                onError = {
                    accessTokenInteractorCallback.onTokenNull()
                },
                onComplete = {
                    accessTokenInteractorCallback.onTokenNull()
                }
            )
        )
    }

    fun fetchLocalAccessToken(){
        val localAccessToken =  AuthRepository.accessToken
        if (localAccessToken.isNullOrEmpty()) {
            fetchAccessTokenFromDB()
        } else {
            accessTokenInteractorCallback.onLocalAccessTokenFetched(localAccessToken)
        }
    }

    interface AccessTokenInteractorCallback {
        fun onLocalAccessTokenFetched(token: String)
        fun onTokenNull()
    }
}

