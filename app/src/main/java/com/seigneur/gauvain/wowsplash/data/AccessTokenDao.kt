package com.seigneur.gauvain.wowsplash.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seigneur.gauvain.wowsplash.data.model.AccessToken
import io.reactivex.Maybe

@Dao
interface AccessTokenDao {

    /**
     * Insert AccessToken in DB
     * return long (transaction id)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccessToken(inAccessToken: AccessToken): Long

    /**
     * @return AccessToken if exists, nothing if not
     */
    @get:Query("SELECT * FROM accesstoken")
    val accessToken: Maybe<AccessToken>

}
