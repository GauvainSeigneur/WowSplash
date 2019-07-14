package com.seigneur.gauvain.wowsplash.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccessToken(
    @PrimaryKey val id:Int= 0,
    val access_token: String,
    val token_type: String,
    val scope: String,
    val created_at: String
)