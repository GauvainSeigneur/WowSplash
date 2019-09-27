package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.model.token.AccessToken
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TokenRepository(private val database: WowSplashDataBase) {

    fun getLocalAccessToken(): Maybe<AccessToken> {
        return database.mAccessTokenDao().accessToken
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        var accessToken: String? = null
    }

}
