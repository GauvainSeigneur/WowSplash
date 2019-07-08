package com.seigneur.gauvain.wowsplash.data.repository

import com.seigneur.gauvain.wowsplash.data.api.UnSplashService
import com.seigneur.gauvain.wowsplash.data.model.Photo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhotoRepository(private val service: UnSplashService)  {

    fun getPhotos(): Flowable<List<Photo>> {
        return service.photos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
