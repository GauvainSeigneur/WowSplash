package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.model.Photo
import io.reactivex.Flowable
import retrofit2.http.GET

interface UnSplashService {

    @GET("photos")
    fun photos(): Flowable<List<Photo>>

    @GET("photos/curated")
    fun curatedPhotos(): Flowable<List<Photo>>

}