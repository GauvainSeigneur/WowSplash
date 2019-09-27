package com.seigneur.gauvain.wowsplash.ui.splash

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy

class SplashViewModel(private val tokenRepository: TokenRepository) : BaseViewModel() {
    val tokenFetch = MutableLiveData<String>()

    fun fetchTokenFromLocal() {
        mDisposables.add(
            tokenRepository.getLocalAccessToken().subscribeBy(
                onSuccess = {
                    TokenRepository.accessToken = it.access_token
                    tokenFetch.postValue("fetched")
                },
                onError = {
                    tokenFetch.postValue("not fetched")
                },
                onComplete = {
                    tokenFetch.postValue("not fetched")
                }
            )
        )
    }

}
