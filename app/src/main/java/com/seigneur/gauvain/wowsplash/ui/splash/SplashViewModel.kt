package com.seigneur.gauvain.wowsplash.ui.splash

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.AccessTokenInteractor
import com.seigneur.gauvain.wowsplash.business.result.AccessTokenResult
import com.seigneur.gauvain.wowsplash.data.repository.AuthRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class SplashViewModel(private val mAuthRepository: AuthRepository) :
    BaseViewModel(), AccessTokenInteractor.AccessTokenInteractorCallback {

    var mTokenResult = MutableLiveData<AccessTokenResult>()

    private val accessTokenInteractor by lazy {
        AccessTokenInteractor(mAuthRepository, mDisposables, this)
    }

    override fun onLocalAccessTokenFetched(accessToken:String) {
        mTokenResult.value = AccessTokenResult.Fetched(accessToken)
    }

    override fun onTokenNull() {
        mTokenResult.value = AccessTokenResult.UnFetched(null)
    }

    fun fetchToken(){
        accessTokenInteractor.fetchAccessTokenFromDB()
    }

}
