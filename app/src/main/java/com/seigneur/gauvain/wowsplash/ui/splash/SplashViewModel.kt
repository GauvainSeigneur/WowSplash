package com.seigneur.gauvain.wowsplash.ui.splash

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.repository.TokenRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SplashViewModel(private val tokenRepository: TokenRepository) : BaseViewModel() {
    val tokenFetch = MutableLiveData<String>()

    fun fetchTokenFromLocal() {
        Timber.d("fetchTokenFromLocal called")
        mDisposables.add(
            tokenRepository.getLocalAccessToken().subscribeBy(
                onSuccess = {
                    TokenRepository.accessToken = it.access_token
                    Timber.d("fetched $it")
                    tokenFetch.postValue("fetched")
                },
                onError = {
                    tokenFetch.postValue("not fetched")
                    Timber.d("not fetched because error $it")
                },
                onComplete = {
                    tokenFetch.postValue("not fetched because null")
                    Timber.d("not fetched because null")
                }
            )
        )
    }

}
