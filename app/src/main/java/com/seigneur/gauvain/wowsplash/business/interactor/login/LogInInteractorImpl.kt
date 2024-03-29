package com.seigneur.gauvain.wowsplash.business.interactor.login

import android.net.Uri
import com.seigneur.gauvain.wowsplash.data.api.AUTH_REDIRECT_URI
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class LogInInteractorImpl(
    private val authRepository: AuthRepository,
    private val presenter: LogInPresenter
) : LogInInteractor {

    val compositeDisposable = CompositeDisposable()

    override fun checkAuthUrl(url: Uri) {
        if (url.toString().startsWith(AUTH_REDIRECT_URI)) {
            val keyCode = url.getQueryParameter("code")
            var accessTokenValue: String? = null
            compositeDisposable.add(authRepository.getAccessTokenFromAPi(keyCode)
                .flatMap {
                    accessTokenValue = it.access_token
                    TokenRepository.accessToken = accessTokenValue
                    return@flatMap authRepository.storeAccessToken(it)
                }
                .map {
                    TokenRepository.accessToken = accessTokenValue
                }
                .subscribeBy(
                    onSuccess = {
                        accessTokenValue?.let {
                            TokenRepository.accessToken = it
                            presenter.onAuthSuccess()
                        }
                    },
                    onError = {
                        presenter.onAuthFailed(it)
                    }
                )
            )
        }
    }

    override fun close() {
        compositeDisposable.clear()
    }
}

