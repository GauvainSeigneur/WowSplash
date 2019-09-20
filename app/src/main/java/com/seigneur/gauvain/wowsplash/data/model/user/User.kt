package com.seigneur.gauvain.wowsplash.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val roomId:Int= 0,
    val id: String,
    val username: String,
    val first_name: String,
    val last_name: String,
    val portfolio_url: String?,
    val bio: String,
    val profile_image: ProfileImage,
    val total_likes: Int,
    val total_photos: Int,
    val total_collections: Int,
    val followed_by_user: Boolean,
    val followers_count: Int,
    val following_count: Int,
    val downloads: Int
)