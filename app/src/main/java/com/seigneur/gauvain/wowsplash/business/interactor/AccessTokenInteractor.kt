package com.seigneur.gauvain.wowsplash.business.interactor

import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class AccessTokenInteractor(private val authRepository: AuthRepository,
                            private val compositeDisposable: CompositeDisposable,
                            private val accessTokenInteractorCallback: AccessTokenInteractorCallback
) {

    fun fetchAccessTokenFromDB(){
        authRepository.accessToken?.let {
            accessTokenInteractorCallback.onLocalAccessTokenFetched(it)
        }?:     accessTokenInteractorCallback.onTokenNull()
    }

    interface AccessTokenInteractorCallback {
        fun onLocalAccessTokenFetched(token: String)
        fun onTokenNull()
    }
}

