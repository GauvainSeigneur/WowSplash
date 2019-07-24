package com.seigneur.gauvain.wowsplash.business.interactor

import android.net.Uri
import com.seigneur.gauvain.wowsplash.data.api.AUTH_REDIRECT_URI
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class LogInInteractor(private val authRepository: AuthRepository,
                      private val compositeDisposable: CompositeDisposable,
                      private val logInCallback: LogInCallback
) {

    fun checkAuthUrl(url: Uri) {
        if (url.toString().startsWith(AUTH_REDIRECT_URI)) {
            val keyCode = url.getQueryParameter("code")
            var accessTokenValue:String?=null
            compositeDisposable.add(authRepository.getAccessTokenFromAPi(keyCode)
                .flatMap {
                    accessTokenValue = it.access_token
                    return@flatMap authRepository.storeAccessToken(it)
                }
                .map {
                    AuthRepository.accessToken = accessTokenValue
                }
                .subscribeBy(
                    onSuccess = {
                        accessTokenValue?.let {
                            logInCallback.onAuthSuccess(it)
                            AuthRepository.accessToken = it
                        }
                    },
                    onError = {
                        Timber.d("onError $it")
                        logInCallback.onAuthFailed(it)
                    }
                )
            )
        }
    }

    interface LogInCallback {
        fun onAuthSuccess(accessToken:String)
        fun onAuthFailed(throwable: Throwable)
    }
}

