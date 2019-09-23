package com.seigneur.gauvain.wowsplash.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seigneur.gauvain.wowsplash.data.model.token.AccessToken
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
    val accessToken: LiveData<AccessToken>

}
