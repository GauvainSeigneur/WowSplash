package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResult
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface UnSplashService {

    /**
     * Photos
     */
    @GET("photos")
    fun photos(): Flowable<List<Photo>>

    @GET("photos")
    fun photos( @Query("page") page: Long,
                @Query("per_page") pagePage: Int): Flowable<List<Photo>>

    /**
     * Collections end point
     */
    @GET("collections")
    fun collections( @Query("page") page: Long,
                @Query("per_page") pagePage: Int): Flowable<List<PhotoCollection>>

    @GET("collections/featured")
    fun featuredCollections( @Query("page") page: Long,
                     @Query("per_page") pagePage: Int): Flowable<List<PhotoCollection>>

    /**
     * Search
     */
    @GET("search/photos")
    fun search(@Query("query") query: String?): Single<SearchResult>

    @POST()
    fun getAccessToken(
        @Url url:String,
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("code") code: String,
        @Query("grant_type") grantType: String
    ): Single<AccessToken>

}