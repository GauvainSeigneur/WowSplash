package com.seigneur.gauvain.wowsplash.data.repository

import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.search.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val service: UnSplashService,
                     private val database: WowSplashDataBase)  {

    var isConnected:Boolean=false

    init {
        listenUserData()
    }

    fun getMe(): Single<User> {
        return service.getMe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchUser(query:String, page:Long, perPage:Int): Single<SearchResponse<User>> {
        return service.searchUser(query, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun listenUserData() {
        database.mUserDao().getUserLive.observeForever(Observer<User> {
            isConnected = it!=null
        })
    }
}
