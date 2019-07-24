package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CollectionsRepository(private val service: UnSplashService)  {

    fun getCollections(page: Long, perPage: Int): Flowable<List<PhotoCollection>> {
        return service.collections(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFeaturedCollections(page: Long, perPage: Int): Flowable<List<PhotoCollection>> {
        return service.featuredCollections(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
