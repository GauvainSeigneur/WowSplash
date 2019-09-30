package com.seigneur.gauvain.wowsplash.business.interactor.login

import android.net.Uri
import com.seigneur.gauvain.wowsplash.data.api.AUTH_REDIRECT_URI
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class LogInInteractorImpl(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
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
                .subscribeBy(
                    onSuccess = {
                        getAndStoreMe()
                    },
                    onError = {
                        presenter.onAuthFailed(it)
                    }
                )
            )
        }
    }

    private fun getAndStoreMe() {
        compositeDisposable.add(
            userRepository.getMe()
                .flatMap {
                    return@flatMap userRepository.insertUserInDataBase(it)
                }
                .subscribeBy(
                    onSuccess = {
                        presenter.onAuthSuccess()
                    },
                    onError = {
                        presenter.onAuthSuccess()
                    })
        )
    }

    override fun close() {
        compositeDisposable.clear()
    }
}

