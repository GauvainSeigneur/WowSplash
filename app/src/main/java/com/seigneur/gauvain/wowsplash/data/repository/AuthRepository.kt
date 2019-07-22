package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.SearchResult
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AuthRepository(private val service: UnSplashService, private val database:WowSplashDataBase)  {

    /**
     * get access token from DB
     */
    fun getAccessTokenFromDB():Maybe<AccessToken> {
        return database.mAccessTokenDao().accessToken
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
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

    companion object {
        //Access Token for API request
        var accessToken: String?=null
    }


}
