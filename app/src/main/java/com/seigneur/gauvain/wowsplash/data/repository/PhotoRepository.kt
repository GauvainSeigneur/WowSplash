package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotoRepository(private val service: UnSplashService)  {

    //get list of Shot from Unsplash
    fun getPhotos(page: Long, perPage: Int, order_by:String?): Flowable<List<Photo>> {
        return service.photos(page, perPage, order_by)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getPhotos(page: Long, perPage: Int): Flowable<List<Photo>> {
        return service.photos(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPhotos(): Flowable<List<Photo>> {
        return service.photos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun likePhoto(id:String):Single<Photo> {
        return service.likePhoto(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /*fun searchPhotos(page: Long, perPage: Int, query:String): Single<Photo> {
        return service.searchPhoto(query, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }*/

}
