package com.seigneur.gauvain.wowsplash.data.model

data class Photo(
    val id: String,
    var username: String? = "Unknown",
    var urls: PhotoUrl,
    var exif: Exif,
    var links: Links,
    var description: String?="No description provided"
)