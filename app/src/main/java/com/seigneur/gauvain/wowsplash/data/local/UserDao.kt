package com.seigneur.gauvain.wowsplash.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seigneur.gauvain.wowsplash.data.model.user.User
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    /**
     * Insert AccessToken in DB
     * return long (transaction id)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Single<Long>

    /**
     *
     */
    @get:Query("SELECT * FROM user")
    val getUser: Maybe<User>

}
