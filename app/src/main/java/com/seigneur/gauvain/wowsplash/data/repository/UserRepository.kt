package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.local.WowSplashDataBase
import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.search.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserRepository(
    private val service: UnSplashService,
    private val database: WowSplashDataBase
) {

    fun getMe(): Single<User> {
        return service.getMe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchUser(query: String, page: Long, perPage: Int): Single<SearchResponse<User>> {
        return service.searchUser(query, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertUserInDataBase(user:User):Single<Long>{
        return database.mUserDao().insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMeFromDataBase(): Maybe<User> {
        return database.mUserDao().getUser
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
