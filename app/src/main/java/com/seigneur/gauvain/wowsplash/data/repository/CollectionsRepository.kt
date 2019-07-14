package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResult
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CollectionsRepository(private val service: UnSplashService)  {

    fun getCollections(page: Long, perPage: Int): Flowable<List<PhotoCollection>> {
        return service.collections(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
