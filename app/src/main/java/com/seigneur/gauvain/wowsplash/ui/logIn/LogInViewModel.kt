package com.seigneur.gauvain.wowsplash.ui.logIn

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.login.LogInInteractor
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class LogInViewModel(private val mAuthRepository: AuthRepository) : BaseViewModel(), LogInPresenter, KoinComponent {

    var mLoginResult = MutableLiveData<LogInResult>()
    var mWebProgressValue = MutableLiveData<Int>()

    private val interactor by inject<LogInInteractor>{ parametersOf(this) }

    override fun onCleared() {
        interactor.close()
        super.onCleared()
    }

    override fun onAuthFailed(throwable: Throwable) {
        Timber.d("auth onAuthFailed")
        mLoginResult.value = LogInResult.LogInError(throwable)
    }

    override fun onAuthSuccess() {
        Timber.d("auth onAuthSuccess")
        mLoginResult.value = LogInResult.LogInSuccess("fuck yeah")
    }

    fun checkAuthUrl(url: Uri) {
        interactor.checkAuthUrl(url)
    }

}

sealed class LogInResult {
    data class LogInSuccess(val message: String) : LogInResult()
    data class LogInError(val inError: Throwable? = null) : LogInResult()
}
