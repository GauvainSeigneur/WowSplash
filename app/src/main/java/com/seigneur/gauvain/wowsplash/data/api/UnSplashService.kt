package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.model.Photo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashService {

    @GET("photos/")
    fun photos(@Query("client_id") clientId: String = "b30c8fca21eb31a472535fceaf4554365dc6b5768320dd5a33745cac7e2a3911"): Flowable<List<Photo>>

}