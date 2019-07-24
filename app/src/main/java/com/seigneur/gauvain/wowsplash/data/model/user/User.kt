package com.seigneur.gauvain.wowsplash.data.model.user

data class User(
    val id: String,
    val username: String,
    val name: String,
    val first_name: String,
    val last_name: String,
    val profile_image:ProfileImage
)