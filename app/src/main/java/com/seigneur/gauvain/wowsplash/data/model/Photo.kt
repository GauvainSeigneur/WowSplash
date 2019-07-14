package com.seigneur.gauvain.wowsplash.data.model

data class Photo(
    val id: String,
    val width : Int,
    val height: Int,
    val color: String? = "#3cb46e",
    var username: String? = "Unknown",
    var urls: PhotoUrl,
    var exif: Exif,
    var links: Links,
    var description: String?="No description provided"
)