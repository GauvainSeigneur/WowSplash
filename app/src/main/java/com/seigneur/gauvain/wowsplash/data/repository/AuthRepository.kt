package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.SearchResult
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepository(private val service: UnSplashService)  {

    fun getAccessToken(code:String): Single<AccessToken> {
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


}
