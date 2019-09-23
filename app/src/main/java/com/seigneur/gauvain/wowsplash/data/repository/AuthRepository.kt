package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.token.AccessToken
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AuthRepository(private val service: UnSplashService, private val database: WowSplashDataBase)  {

    var accessToken:String?=null

    init {
        listenLiveData()
    }


    /**
     * Store Task in database
     */
    fun storeAccessToken (accessToken: AccessToken): Single<Long> {
        Timber.d("storeAccessToken called $accessToken")
        return Single.fromCallable {
            database.mAccessTokenDao().insertAccessToken(accessToken)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * fetch access token from db
     */
    fun getAccessTokenFromAPi(code:String): Single<AccessToken> {
        return service.getAccessToken(
            TOKEN_URL_REQUEST,
            CLIENT_ID_VALUE,
            CLIENT_SECRET_VALUE,
            AUTH_REDIRECT_URI,
            code,
            GRANT_TYPE_VALUE
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun listenLiveData() {
        database.mAccessTokenDao().accessToken.observeForever {
            accessToken = it.access_token
            constAccessToken = accessToken

        }
    }

    companion object {
        //Access Token for API request
        var constAccessToken :String?=null
    }


}
