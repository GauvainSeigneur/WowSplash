package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.user.User
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserRepository(private val service: UnSplashService,
                     private val database: WowSplashDataBase)  {

    fun getMe(): Single<User> {
        return service.getMe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
