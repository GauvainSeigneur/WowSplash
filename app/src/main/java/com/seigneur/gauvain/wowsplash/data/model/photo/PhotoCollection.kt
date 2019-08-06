package com.seigneur.gauvain.wowsplash.data.model.photo

data class PhotoCollection(
    val id: String,
    var username: String? = "Unknown",
    var cover_photo: Photo? = null, //todo make a photo object by default which hold related data
    val title: String? = "No title provided",
    val description: String? = "No description provided",
    val curated: Boolean? = false
) {
    companion object {
    }
}