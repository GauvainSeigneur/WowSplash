package com.seigneur.gauvain.wowsplash.ui.logIn

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.LogInInteractor
import com.seigneur.gauvain.wowsplash.business.result.LogInResult
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class LogInViewModel(private val mAuthRepository: AuthRepository) : BaseViewModel(), LogInInteractor.LogInCallback {

    var mLoginResult = MutableLiveData<LogInResult>()
    var mWebProgressValue = MutableLiveData<Int>()

    val loginInteractor by lazy {
        LogInInteractor(mAuthRepository, mDisposables, this)
    }

    override fun onAuthFailed(throwable: Throwable) {
        mLoginResult.value = LogInResult.LogInError(throwable)
    }

    override fun onAuthSuccess(accessToken: String) {
        mLoginResult.value = LogInResult.LogInSuccess(accessToken)
    }

    fun checkAuthUrl(url: Uri) {
        loginInteractor.checkAuthUrl(url)
    }

}
