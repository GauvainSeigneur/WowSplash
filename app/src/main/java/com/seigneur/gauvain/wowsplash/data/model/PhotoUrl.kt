package com.seigneur.gauvain.wowsplash.data.model

data class PhotoUrl(
    var thumb: String,
    var small: String? = thumb,
    var regular: String? = small,
    var full: String? = regular,
    var raw: String? = full
)