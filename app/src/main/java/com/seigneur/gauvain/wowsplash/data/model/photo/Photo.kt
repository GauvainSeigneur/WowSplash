package com.seigneur.gauvain.wowsplash.data.model.photo

import android.os.Parcelable
import com.seigneur.gauvain.wowsplash.data.model.user.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val color: String? = "#3cb46e",
    var user: User,
    var urls: PhotoUrl,
    var exif: Exif? = Exif(),
    var links: Links,
    var description: String? = "No description provided",
    var liked_by_user: Boolean,
    var current_user_collections: List<PhotoCollection>? = null
) : Parcelable