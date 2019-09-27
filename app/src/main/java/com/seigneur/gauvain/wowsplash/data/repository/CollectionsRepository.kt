package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.*
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
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

    fun getFeaturedCollections(page: Long, perPage: Int): Flowable<List<PhotoCollection>> {
        return service.featuredCollections(page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserCollections(userName:String, page: Long, perPage: Int) :Flowable<List<PhotoCollection>>{
        return service.userCollections(userName, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerPhotoInCollection(collectionId: String, photoId: String): Single<Photo> {
        return service.addPhotoToCollection(collectionId, photoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun removePhotoFromCollection(collectionId: String, photoId: String): Single<Photo> {
        return service.removePhotoFromCollection(collectionId, photoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
