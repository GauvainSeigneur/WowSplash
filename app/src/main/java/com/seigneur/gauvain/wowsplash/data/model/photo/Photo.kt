package com.seigneur.gauvain.wowsplash.data.model.photo

import com.seigneur.gauvain.wowsplash.data.model.user.User

data class Photo(
    val id: String,
    val width : Int,
    val height: Int,
    val color: String? = "#3cb46e",
    var user: User? =null,
    var urls: PhotoUrl,
    var exif: Exif,
    var links: Links,
    var description: String?="No description provided",
    var liked_by_user:Boolean
)