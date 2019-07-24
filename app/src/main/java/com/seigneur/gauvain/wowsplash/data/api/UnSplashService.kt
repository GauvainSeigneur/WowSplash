package com.seigneur.gauvain.wowsplash.data.api

import com.seigneur.gauvain.wowsplash.data.model.*
import com.seigneur.gauvain.wowsplash.data.model.user.User
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

interface UnSplashService {

    /**
     * Photos
     */
    @GET("photos")
    fun photos(): Flowable<List<Photo>>

    @GET("photos")
    fun photos( @Query("page") page: Long,
                @Query("per_page") pagePage: Int): Flowable<List<Photo>>

    @GET("photos")
    fun photos( @Query("page") page: Long,
                @Query("per_page") pagePage: Int,
                @Query("order_by") value: String?): Flowable<List<Photo>>

    /**
     * Like a photo
     */
    @POST("photos/{id}/like")
    fun likePhoto(
        @Path("id") id:String): Single<Photo>

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
     * Oauth2
     */
    @POST()
    fun getAccessToken(
        @Url url:String,
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("code") code: String,
        @Query("grant_type") grantType: String
    ): Single<AccessToken>


    /**
     * User
     */
    @GET("me")
    fun getMe(): Single<User>

    /**
     * Search
     */
    @GET("search/photos")
    fun searchPhoto(@Query("query") query: String?): Single<SearchResponse<Photo>>

    @GET("search/photos")
    fun searchPhoto(@Query("query") query: String?,
                    @Query("page") page: Long,
                    @Query("per_page") pagePage: Int): Single<SearchResponse<Photo>>

    @GET("search/collections")
    fun searchCollection(@Query("query") query: String?): Single<SearchResponse<PhotoCollection>>

    @GET("search/users")
    fun searchUser(@Query("query") query: String?): Single<SearchResponse<User>>
}