package com.seigneur.gauvain.wowsplash.ui.logIn

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.api.AUTH_REDIRECT_URI
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class LogInViewModel(private val mAuthRepository: AuthRepository) : BaseViewModel() {

    var mLoginResult = MutableLiveData<LoginResult>()
    var mWebProgressValue = MutableLiveData<Int>()

    fun checkAuthUrl(url: Uri) {
        if (url.toString().startsWith(AUTH_REDIRECT_URI)) {
            val keyCode  = url.getQueryParameter("code")
            mDisposables.add(
                mAuthRepository.getAccessToken(keyCode)
                    .subscribeBy(
                        onSuccess = {
                            Timber.d("accesstoken rceived $it")
                            mLoginResult.value = LoginResult.LogInSuccess(it.access_token)
                            //todo - save in DB !
                        },
                        onError = {
                            Timber.d("onError $it")
                            mLoginResult.value = LoginResult.LogInError(it)
                            //mListResult.value = ListResult(inError = it)
                        }
                    )
            )
        }
    }

    sealed class LoginResult {
        data class LogInSuccess(val accessToken: String) : LoginResult()
        data class LogInError(val inError: Throwable? = null) : LoginResult()
    }

}
